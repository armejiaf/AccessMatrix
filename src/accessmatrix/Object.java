/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package accessmatrix;

import java.util.TreeMap;

/**
 *
 * @author Allan Mejia
 */
public class Object {
    TreeMap<String,Privileges> domains;
    public Object()
    {
        domains = new TreeMap<>();
    }
}
