package interview;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;

@Path("/endpoint")
@Component
public class Endpoint {

    private CarService carService;

    @Autowired
    public Endpoint(CarService carService) {
        this.carService = carService;
    }

    @Path("/cars/{carType}")
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public @ResponseBody CarResource getCars() {
        return new CarResource(carService.getCars(CarType.SEDAN));
    }

    @Path("/cars/super")
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public @ResponseBody CarResource
    getSuperCars() {
        return new CarResource(carService.getCars(CarType.SUPER));
    }

    @Path("/cars/truck")
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public @ResponseBody CarResource
    getTrucks() {
        return new CarResource(carService.getCars(CarType.TRUCK));
    }

    @Path("/cars/{id}{car_type}/year/{year}/make/{make}/{model}/color/{color}")
    @PUT
    @Consumes(MediaType.APPLICATION_JSON)
    public CarResource
    addCar(@PathParam("car_type") CarType carType,
           @PathParam("year") String year,
           @PathParam("make") String make,
           @PathParam("model") String model,
           @PathParam("color") String color) {
        Car car = new Car(carType, make, model, year);
        return new CarResource(carService.addCar(car));
    }

    @Path("/cars/{car_type}/year/{year}/color/{color}")
    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    public CarResource addCar(@PathParam("car_type") CarType carType,
                              @PathParam("year") String year,
                              @PathParam("color") String color,
                              CarUpdateResource carUpdateResource) {
        return new CarResource(carService.updateCar(carType, year, carUpdateResource.getColor()));
    }
}
