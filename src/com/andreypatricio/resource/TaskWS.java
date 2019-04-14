package com.andreypatricio.resource;

import com.andreypatricio.controller.TaskController;

import javax.ws.rs.*;

@Path("task")
public class TaskWS {
	
	TaskController controller;
	
	public TaskWS() {
		this.controller = new TaskController();
	}
	
	@GET
	@Path("get")
	@Produces("application/json")
	public String getTask(){
		return controller.getTask();
	}
	
	@POST
	@Path("new")
	@Consumes({"application/json"})
	public void newTask(String json){
		controller.newTask(json);
	}
	
	@DELETE
	@Path("delete/{id}")
	@Consumes({"application/json"})
	public void deleteTask(@PathParam("id") String id){
		controller.deleteTask(id);
	}

	@PUT
	@Path("done/{id}")
	@Consumes({"application/json"})
	public void doneTask(@PathParam("id") String id){
		controller.doneTask(id);
	}

}
