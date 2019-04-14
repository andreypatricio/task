package com.andreypatricio.controller;

import com.andreypatricio.dao.TaskDAO;

public class TaskController {
	
	TaskDAO dao;
	
	public TaskController(){
		this.dao = new TaskDAO();
	}

	public String getTask() {
		return dao.getTasks();
	}

	public void newTask(String json) {
		dao.newTask(json);
	}

	public void deleteTask(String id) {
		dao.deleteTask(id);
		
	}

	public void doneTask(String id) {
		dao.doneTask(id);

	}

}
