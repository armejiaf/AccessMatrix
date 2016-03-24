/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package accessmatrix;

/**
 *
 * @author Allan Mejia
 */
public class Privileges {
    boolean execute=false;
    boolean read=false;
    boolean write=false;

    public Privileges(boolean execute, boolean read, boolean write) {
        this.execute = execute;
        this.read = read;
        this.write = write;
    }

    public boolean getExecute() {
        return execute;
    }

    public void setExecute(boolean execute) {
        this.execute = execute;
    }

    public boolean getRead() {
        return read;
    }

    public void setRead(boolean read) {
        this.read = read;
    }

    public boolean getWrite() {
        return write;
    }

    public void setWrite(boolean write) {
        this.write = write;
    }
    
    
}
