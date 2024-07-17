package com.shopme.checkout;

import java.io.UnsupportedEncodingException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.List;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import javax.servlet.http.HttpServletRequest;

import com.shopme.common.entity.*;
import com.shopme.setting.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import com.shopme.Utility;
import com.shopme.address.AddressService;
import com.shopme.checkout.paypal.PayPalApiException;
import com.shopme.checkout.paypal.PayPalService;
import com.shopme.common.entity.order.Order;
import com.shopme.common.entity.order.PaymentMethod;
import com.shopme.customer.CustomerService;
import com.shopme.order.OrderService;
import com.shopme.shipping.ShippingRateService;
import com.shopme.shoppingcart.ShoppingCartService;

@Controller
public class CheckoutController {

	@Autowired private CheckoutService checkoutService;
	@Autowired private CustomerService customerService;
	@Autowired private AddressService addressService;
	@Autowired private ShippingRateService shipService;
	@Autowired private ShoppingCartService cartService;
	@Autowired private OrderService orderService;
	@Autowired private SettingService settingService;
	@Autowired private PayPalService paypalService;
	@Autowired private CountryRepository countryRepository;

	@GetMapping("/checkout")
	public String showCheckoutPage(Model model, HttpServletRequest request) {
		Customer customer = getAuthenticatedCustomer(request);

		Address defaultAddress = addressService.getDefaultAddress(customer);
		ShippingRate shippingRate = null;

		if (defaultAddress != null) {
			model.addAttribute("shippingAddress", defaultAddress.toString());
			shippingRate = shipService.getShippingRateForAddress(defaultAddress);
		} else {
			model.addAttribute("shippingAddress", customer.toString());
			shippingRate = shipService.getShippingRateForCustomer(customer);
		}

		if (shippingRate != null) {
			return "redirect:/cart";
		}

		ShippingRate shippingRate1=new ShippingRate();
		shippingRate1.setRate(1);
		shippingRate1.setDays(1);
		shippingRate1.setCodSupported(true);
		Country country=countryRepository.findByCode("tr");
		shippingRate1.setCountry(country);

		List<CartItem> cartItems = cartService.listCartItems(customer);
		CheckoutInfo checkoutInfo = checkoutService.prepareCheckout(cartItems, shippingRate1);

		String currencyCode = settingService.getCurrencyCode();
		PaymentSettingBag paymentSettings = settingService.getPaymentSettings();
		String paypalClientId = paymentSettings.getClientID();

		model.addAttribute("paypalClientId", paypalClientId);
		model.addAttribute("currencyCode", currencyCode);
		model.addAttribute("customer", customer);
		model.addAttribute("checkoutInfo", checkoutInfo);
		model.addAttribute("cartItems", cartItems);

		return "checkout/checkout";
	}

	private Customer getAuthenticatedCustomer(HttpServletRequest request) {
		String email = Utility.getEmailOfAuthenticatedCustomer(request);
		return customerService.getCustomerByEmail(email);
	}

	@PostMapping("/place_order")
	public String placeOrder(HttpServletRequest request)
			throws UnsupportedEncodingException, MessagingException {
		String paymentType = request.getParameter("paymentMethod");
		PaymentMethod paymentMethod = PaymentMethod.valueOf(paymentType);

		Customer customer = getAuthenticatedCustomer(request);

		Address defaultAddress = addressService.getDefaultAddress(customer);
		ShippingRate shippingRate = null;

		if (defaultAddress != null) {
			shippingRate = shipService.getShippingRateForAddress(defaultAddress);
		} else {
			shippingRate = shipService.getShippingRateForCustomer(customer);
		}

		List<CartItem> cartItems = cartService.listCartItems(customer);
		CheckoutInfo checkoutInfo = checkoutService.prepareCheckout(cartItems, shippingRate);

		Order createdOrder = orderService.createOrder(customer, defaultAddress, cartItems, paymentMethod, checkoutInfo);
		cartService.deleteByCustomer(customer);
		//sendOrderConfirmationEmail(request, createdOrder);

		return "checkout/order_completed";
	}

//	private void sendOrderConfirmationEmail(HttpServletRequest request, Order order)
//			throws UnsupportedEncodingException, MessagingException {
//		EmailSettingBag emailSettings = settingService.getEmailSettings();
//		JavaMailSenderImpl mailSender = Utility.prepareMailSender(emailSettings);
//		mailSender.setDefaultEncoding("utf-8");
//
//		String toAddress = order.getCustomer().getEmail();
//		String subject = emailSettings.getOrderConfirmationSubject();
//		String content = emailSettings.getOrderConfirmationContent();
//
//		subject = subject.replace("[[orderId]]", String.valueOf(order.getId()));
//
//		MimeMessage message = mailSender.createMimeMessage();
//		MimeMessageHelper helper = new MimeMessageHelper(message);
//
//		helper.setFrom(emailSettings.getFromAddress(), emailSettings.getSenderName());
//		helper.setTo(toAddress);
//		helper.setSubject(subject);
//
//		DateFormat dateFormatter =  new SimpleDateFormat("HH:mm:ss E, dd MMM yyyy");
//		String orderTime = dateFormatter.format(order.getOrderTime());
//
//		CurrencySettingBag currencySettings = settingService.getCurrencySettings();
//		String totalAmount = Utility.formatCurrency(order.getTotal(), currencySettings);
//
//		content = content.replace("[[name]]", order.getCustomer().getFullName());
//		content = content.replace("[[orderId]]", String.valueOf(order.getId()));
//		content = content.replace("[[orderTime]]", orderTime);
//		content = content.replace("[[shippingAddress]]", order.getShippingAddress());
//		content = content.replace("[[total]]", totalAmount);
//		content = content.replace("[[paymentMethod]]", order.getPaymentMethod().toString());
//
//		helper.setText(content, true);
//		mailSender.send(message);
//	}

	@PostMapping("/process_paypal_order")
	public String processPayPalOrder(HttpServletRequest request, Model model)
			throws UnsupportedEncodingException, MessagingException {
		String orderId = request.getParameter("orderId");

		String pageTitle = "Checkout Failure";
		String message = null;

		try {
			if (paypalService.validateOrder(orderId)) {
				return placeOrder(request);
			} else {
				pageTitle = "Checkout Failure";
				message = "ERROR: Transaction could not be completed because order information is invalid";
			}
		} catch (PayPalApiException e) {
			message = "ERROR: Transaction failed due to error: " + e.getMessage();
		}

		model.addAttribute("pageTitle", pageTitle);
		model.addAttribute("title", pageTitle);
		model.addAttribute("message", message);

		return "message";
	}
}
