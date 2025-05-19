package ch08;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;

@Path("/test")
public class RestApiExample {
  @GET
  @Produces(MediaType.TEXT_PLAIN)
  public String get() {
    return "Hello World";
  }

  @POST
  public String post(@QueryParam("msg") String msg) {
    return msg + " : API service";
  }
}
