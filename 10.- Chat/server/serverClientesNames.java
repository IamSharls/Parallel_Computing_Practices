package server;

import cliente.clienteInterfaz;

public class serverClientesNames {
	public String nombre;
	public clienteInterfaz cliente;
	
	public serverClientesNames(String nombre, clienteInterfaz cliente){
		this.nombre = nombre;
		this.cliente = cliente;
	}

	public String getNombre(){
		return nombre;
	}
	public clienteInterfaz getClient(){
		return cliente;
	}
	
}