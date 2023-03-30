package com.openwebinars.rest.Controller;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.ResponseBody;

import com.openwebinars.rest.Service.ServicioUploadHandler;

import lombok.RequiredArgsConstructor;

@Controller
@RequiredArgsConstructor
public class FicherosController {

	@Autowired
	ServicioUploadHandler servicio;

	@GetMapping(value = "/files/{filename:.+}")
	@ResponseBody
	public ResponseEntity<Resource> serveFile(@PathVariable String filename, HttpServletRequest request) {
		return servicio.serveFileHandler(filename, request);
	}

}