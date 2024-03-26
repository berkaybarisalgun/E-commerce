$(document).ready(function (){
    $("#buttonCancel").on("click",function (){
        window.location=moduleUrl;
    });
    $("#fileImage").change(function (){
        this.setCustomValidity("");
        showImageThumbnail(this);
        fileSize=this.files[0].size;
        console.log(fileSize);
        if(fileSize>102400000){//file size editor
            this.setCustomValidity("You must choose an image less tahn 10000KB!");
            this.reportValidity();
        }else{
            this.setCustomValidity("");
            showImageThumbnail(this);
        }
    })
});
function showImageThumbnail(fileInput){
    var file=fileInput.files[0];
    var reader=new FileReader();
    reader.onload=function (e){
        $("#thumbnail").attr("src",e.target.result);
    };
    reader.readAsDataURL(file);
}

function showModalDialog(title, message) {
    $("#modalTitle").text(title);
    $("#modalBody").text(message);
    $("#modalDialog").modal();
}

function showErrorModal(message) {
    showModalDialog("Error", message);
}

function showWarningModal(message) {
    showModalDialog("Warning", message);
}