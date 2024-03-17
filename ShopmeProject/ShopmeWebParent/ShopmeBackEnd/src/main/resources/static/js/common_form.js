$(document).ready(function (){
    $("#buttonCancel").on("click",function (){
        window.location=moduleUrl;
    });
    $("#fileImage").change(function (){
        fileSize=this.files[0].size;
        if(fileSize>102400){
            this.setCustomValidity("You must choose an image less tahn 100KB!");
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