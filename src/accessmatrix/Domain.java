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
public class Domain {
    TreeMap<String,Privileges> objects ;
    public Domain()
    {
        objects = new TreeMap<>();
    }
}
