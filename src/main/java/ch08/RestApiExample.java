package ch08;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;

@Path("/test")
public class RestApiExample {
  private String msg;

  @GET
  @Produces(MediaType.TEXT_PLAIN)
  public String get() {
    return "Hello World";
  }

  @POST
  @Path("/{msg}")
  @Consumes(MediaType.APPLICATION_JSON)
  public String post(@PathParam("msg") String msg) {
    return msg + " : API service";
  }
}
