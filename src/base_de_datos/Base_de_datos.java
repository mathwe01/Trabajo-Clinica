/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Main.java to edit this template
 */
package base_de_datos;
import conexion.Conexion;
import java.sql.Connection;
import java.sql.SQLException;
 
/**
 * Clase de prueba: solo sirve para verificar que el sistema logra
 * conectarse a la base de datos. Ejecuta este main() en NetBeans.
 *
 * Autor: Mathew
 */
public class Base_de_datos {
 
    public static void main(String[] args) {
        try (Connection con = Conexion.obtenerConexion()) {
            if (con != null) {
                System.out.println("Conexion exitosa a la base de datos.");
            }
        } catch (SQLException e) {
            System.out.println("Error al conectar con la base de datos: " + e.getMessage());
        }
    }
}