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
import com.example.deviceStorageJDBC.domain.ToOrder;
import com.example.deviceStorageJDBC.manager.StorageManager;
import com.example.deviceStorageJDBC.manager.ToOrderManager;

@Path("toorder")
public class ToOrderRESTService {
	
	ToOrderManager toOrderManager = new ToOrderManager();
	StorageManager storageManager = new StorageManager();
	
	@GET
	@Path("/{idOrder}")
	@Produces(MediaType.APPLICATION_JSON)
	public ToOrder getOrderById(@PathParam("idOrder") long idOrder){
		ToOrder toOrder = toOrderManager.getOrderById(idOrder);
		return toOrder;
	}
	
	@GET
	@Path("/all")
	@Produces(MediaType.APPLICATION_JSON)
	public List<ToOrder> getAllOrders(){
		List <ToOrder> toOrders = toOrderManager.getAllOrders();
		return toOrders;
	}
	
	@GET
	@Path("/count")
	@Produces(MediaType.TEXT_PLAIN)
	public String getCountOrders(){
		String count = Integer.toString(toOrderManager.getCount());
		return count;
	}
	
	
	@GET
	@Path("/ordersForDevice/{idPosition}")
	@Produces(MediaType.APPLICATION_JSON)
	public List<ToOrder> getOrdersForDevice(@PathParam("idPosition") long id_position){
		Storage position = new Storage();
		position = storageManager.getPositionById(id_position);
		List <ToOrder> orderedPositions = toOrderManager.getAllOrdersForDeviceInStorage(position);
		return orderedPositions;
	}
	
	@POST
	@Path("/")
	@Consumes(MediaType.APPLICATION_JSON)
	public Response addOrder(ToOrder toOrder){
		toOrderManager.addOrder(toOrder);
		return Response.status(201).entity("ToOrder").build(); 
	}
	
	@PUT
	@Path("/{idOrder}/{idPosition}")
	public Response updatePositions(@PathParam("idOrder") long idOrder, @PathParam("idPosition") long idPosition) throws SQLException{
		Storage position = new Storage();
		position = storageManager.getPositionById(idPosition);
		
		ToOrder toOrder = new ToOrder();
		toOrder = toOrderManager.getOrderById(idOrder);
		
		toOrderManager.updateForeignKey(toOrder, position);
		return Response.status(200).entity("ToOrder").build(); 
	}
	
	@DELETE
	public Response removeOrders() throws SQLException{
		toOrderManager.removeOrders();
		return Response.status(200).build();
	}
	
	@DELETE
	@Path("/{idOrder}")
	public Response removeOneOrder(@PathParam("idOrder") long idOrder) throws SQLException{
		ToOrder toOrder = toOrderManager.getOrderById(idOrder);
		toOrderManager.removeOneOrder(toOrder);
		return Response.status(200).build();
	}

}
