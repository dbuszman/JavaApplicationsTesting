package com.example.deviceStorageJDBC.rest;

import java.sql.SQLException;
import java.util.List;

import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.example.deviceStorageJDBC.domain.Storage;
import com.example.deviceStorageJDBC.manager.StorageManager;
import com.example.deviceStorageJDBC.manager.ToOrderManager;

@Path("storage")
public class StorageRESTService {
	
	StorageManager storageManager = new StorageManager();
	ToOrderManager toOrderManager = new ToOrderManager();

	@GET
	@Path("/{idPosition}")
	@Produces(MediaType.APPLICATION_JSON)
	public Storage getPositionById(@PathParam("idPosition") long id_position){
		Storage position = storageManager.getPositionById(id_position);
		return position;
	}
	
	@GET
	@Path("/all")
	@Produces(MediaType.APPLICATION_JSON)
	public List<Storage> getAllPositions(){
		List <Storage> position = storageManager.getAllPositions();
		return position;
	}
	
	@GET
	@Path("/orderedPositions")
	@Produces(MediaType.APPLICATION_JSON)
	public List<Storage> getOrderedPositions(){
		List <Storage> orderedPositions = toOrderManager.getOrderedPositions();
		return orderedPositions;
	}
	
	@POST
	@Path("/")
	@Consumes(MediaType.APPLICATION_JSON)
	public Response addPosition(Storage position){
		storageManager.addPosition(position);
		return Response.status(201).entity("Storage").build(); 
	}
	
	@PUT
	@Path("/{defaultMargin}/{minAmount}")
	public Response updatePositions(@PathParam("defaultMargin") int defaultMargin, @PathParam("minAmount") int minAmount) throws SQLException{
		storageManager.updatePositions(defaultMargin, minAmount);
		return Response.status(200).entity("Storage").build(); 
	}
	
	@GET
	@Path("/test")
	@Produces(MediaType.TEXT_HTML)
	public String test(){
		return "REST API /storage is running";
	}
	
	@DELETE
	public Response removePositions() throws SQLException{
		storageManager.removePositions();
		return Response.status(200).build();
	}
}
