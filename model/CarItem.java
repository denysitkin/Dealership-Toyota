package toyota_app.model;

public class CarItem {

    private String carModel;
    private String bodyType;
    private String imgSrc;

    public CarItem() {}

    public CarItem(String imgSrc, String carModel, String bodyType) {
        this.carModel = carModel;
        this.imgSrc = imgSrc;
        this.bodyType = bodyType;
    }

    public String getCarModel() {
        return carModel;
    }

    public void setCarModel(String modelCar) {
        this.carModel = modelCar;
    }

    public String getBodyType() {
        return bodyType;
    }

    public void setBodyType(String bodyType) {
        this.bodyType = bodyType;
    }

    public String getImgSrc() {
        return imgSrc;
    }

    public void setImgSrc(String imgSrc) {
        this.imgSrc = imgSrc;
    }
}
