/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package accessmatrix;

import java.util.Iterator;
import java.util.Random;
import java.util.Scanner;
import java.util.TreeMap;

/**
 *
 * @author Allan Mejia
 */
public class AccessMatrix {
    
    int domainCount=0;
    int objectCount=0;
    String dName,oName;
    /**
    * @param args the command line arguments
    */
    public static void main(String[] args) {
        // TODO code application logic here
        AccessMatrix accessMatrix = new AccessMatrix();
        Scanner reader = new Scanner(System.in); 
       
        TreeMap<String,Domain> domains= accessMatrix.getDomains();
        TreeMap<String,Object> objects = accessMatrix.getObjects();
        accessMatrix.randomizePrivileges(domains,objects);
        
        System.out.println();
        int option;
        while(true)
        {
            try{
                accessMatrix.printMenu();
                option = reader.nextInt();
                if(option == 7)
                    break;

                switch(option){
                    case 1:
                        accessMatrix.listPrivilegesDomains(domains);
                        break;
                    case 2:
                        accessMatrix.listPrivilegesObjects(objects);
                        break;
                    case 3:
                        accessMatrix.addRemoveDomain(domains,objects);
                        break;
                    case 4:
                        accessMatrix.addRemoveObject(domains,objects);
                        break;
                    case 5: 
                        accessMatrix.grantRevokePrivileges(domains,objects);
                        break;
                    case 6:
                        accessMatrix.verifyAction(domains,objects);
                        break;
                    default:
                        System.out.println();
                        System.out.println("Opcion invalida!");
                        System.out.println();
                }
            }catch(Exception e){
                System.out.println("Opcion invalida!");
                System.out.println();
                reader.next();
            }
        }    
    }

    private void printMenu() {
        System.out.println();
        System.out.println("------------------------------------------------");
        System.out.println("1. Listar privilegios por dominio.");
        System.out.println("2. Listar privilegios por objeto.");
        System.out.println("3. Agregar/Eliminar dominio.");
        System.out.println("4. Agregar/Eliminar objeto.");
        System.out.println("5. Otorgar/Revocar privilegios.");
        System.out.println("6. Verificar accion.");
        System.out.println("7. Salir.");
        System.out.println("------------------------------------------------");
        System.out.println();
        System.out.print("Escoja opcion: ");
    }

    private TreeMap<String, Domain> getDomains() {
        Scanner reader = new Scanner(System.in);  
        TreeMap<String,Domain> domainsToReturn = new TreeMap<>();
        int domains;
        while(true){
            try{
                System.out.print("Ingrese cantidad de dominios: ");
                domains = reader.nextInt();
                break;
            }catch(Exception e){
                System.out.println();
                System.out.println("Ingrese un numero!");
                reader.next();
            }
        }
        if (domains <= 0)
            domains = 1;
        String name;
        for(int i=0; i<domains;i++){
            domainCount=i;
            name = getDomainName();
            domainsToReturn.put(name, new Domain());
        }
        return domainsToReturn; 
    }

    private TreeMap<String, Object> getObjects(){
        Scanner reader = new Scanner(System.in);
        TreeMap<String,Object> objectsToReturn = new TreeMap<>();
        int objects;
        while(true){
            try{
                System.out.print("Ingrese cantidad de objetos: ");
                objects = reader.nextInt();
                break;
            }catch(Exception e){
                System.out.println();
                System.out.println("Ingrese un numero!");
                reader.next();
            }
        }
        if (objects <= 0)
            objects = 1;
        String name;
        for(int i=0; i<objects;i++){
            objectCount=i;
            name = getObjectName();
            objectsToReturn.put(name, new Object());
            
        }
        return objectsToReturn; 
    }

    private void randomizePrivileges(TreeMap<String, Domain> domains, TreeMap<String, Object> objects) {
        Random rnd = new Random();
        Iterator<Domain> domainsFiles = domains.values().iterator();
        Iterator<String> domainsNames = domains.keySet().iterator();
        Iterator<Object> objectsFiles;
        Iterator<String> objectsNames;
    
        while(domainsNames.hasNext()){
            String domainName = domainsNames.next();
            Domain domain = domainsFiles.next();
            objectsNames = objects.keySet().iterator();
            objectsFiles = objects.values().iterator();
            while(objectsNames.hasNext()){
                String objectName = objectsNames.next();
                Object object = objectsFiles.next();
                Privileges privilages = new Privileges(rnd.nextBoolean(), rnd.nextBoolean(), rnd.nextBoolean());
                object.domains.put(domainName, privilages);
                domain.objects.put(objectName, privilages);
                domains.put(domainName, domain);
                objects.put(objectName, object);
            }
        }
        
    }

    private void listPrivilegesDomains(TreeMap<String, Domain> domains) {
        Iterator<Domain> domainsFiles = domains.values().iterator();
        Iterator<String> domainsNames = domains.keySet().iterator();
        Iterator<String> objectsNames; 
        while(domainsNames.hasNext()){
            String domainName = domainsNames.next();
            Domain domain = domainsFiles.next();
            objectsNames = domain.objects.keySet().iterator();
            System.out.println();
            System.out.println("**************************************************");
            System.out.println("Dominio: "+domainName);
            while(objectsNames.hasNext()){
                String objectName = objectsNames.next();
                Privileges privileges = domain.objects.get(objectName);
                System.out.println("Sus privilegios para el objeto "+objectName);
                System.out.println("Leer: "+privileges.getRead()+
                                    "\nEscribir: "+privileges.getWrite()+
                                    "\nEjecutar: "+privileges.getExecute());
            }
            System.out.println("**************************************************");
         }
    }

    private void listPrivilegesObjects(TreeMap<String, Object> objects) {
        Iterator<Object> objectsFiles = objects.values().iterator();
        Iterator<String> objectsNames = objects.keySet().iterator();
        Iterator<String> domainsNames; 
        while(objectsNames.hasNext()){
            String objectName = objectsNames.next();
            Object object = objectsFiles.next();
            domainsNames = object.domains.keySet().iterator();
            System.out.println();
            System.out.println("**************************************************");
            System.out.println("Objeto: "+objectName);
            while(domainsNames.hasNext()){
                String domainName = domainsNames.next();
                Privileges privileges = object.domains.get(domainName);
                System.out.println("Privilegios del dominio "+domainName);
                System.out.println("Leer: "+privileges.getRead()+
                                    "\nEscribir: "+privileges.getWrite()+
                                    "\nEjecutar: "+privileges.getExecute());
            }
            System.out.println("**************************************************");
         }
    }

    private void addRemoveDomain(TreeMap<String, Domain> domains, TreeMap<String, Object> objects) {
        Scanner reader = new Scanner(System.in);
        System.out.println();
        System.out.println("1. Agregar dominio.");
        System.out.println("2. Eliminar dominio.");
        System.out.print("Desea agregar o eliminar un dominio: ");
         int option;
        try{
            option = reader.nextInt();
        }catch(Exception e){
            reader.next();
            option=0;
        }
        if(option == 1)
            addDomain(domains,objects);
        else if(option == 2)
            removeDomain(domains,objects);
        else{
            System.out.println();
            System.out.println("Opcion invalida!");
        }
            
    }

    private void addRemoveObject(TreeMap<String, Domain> domains, TreeMap<String, Object> objects) {
        Scanner reader = new Scanner(System.in); 
        System.out.println();
        System.out.println("1. Agregar objeto.");
        System.out.println("2. Eliminar objeto.");
        System.out.print("Desea agregar o eliminar un objeto: ");
        int option;
        try{
            option = reader.nextInt();
        }catch(Exception e){
            reader.next();
            option=0;
        }
        if(option == 1)
            addObject(domains,objects);
        else if(option == 2)
            removeObject(domains,objects);
        else{
            System.out.println();
            System.out.println("Opcion invalida!");
        }
    }

    private void grantRevokePrivileges(TreeMap<String, Domain> domains,TreeMap<String, Object> objects) {
        Scanner reader = new Scanner(System.in); 
        System.out.println();
        System.out.println("1. Otorgar privilegio.");
        System.out.println("2. Revocar privilegio.");
        System.out.print("Desea otrogar o revocar un privilegio: ");
        int option;
        try{
            option = reader.nextInt();
        }catch(Exception e){
            reader.next();
            option=0;
        }
        if(option == 1)
            grantPrivilege(domains,objects);
        else if(option == 2)
            revokePrivilege(domains,objects);
        else{
            System.out.println();
            System.out.println("Opcion invalida!");
        }
    }

    private void verifyAction(TreeMap<String, Domain> domains,TreeMap<String, Object> objects) {
        if(existsDomainObject(domains, objects)){
                executeAction(domains);
        }
    }

    private void addDomain(TreeMap<String, Domain> domains,TreeMap<String, Object> objects) {
        domainCount++;
        String domainName=getDomainName();
        Domain domain = new Domain();
        Privileges privileges = new Privileges(false, false, false);
        Iterator<Object> objectsFiles = objects.values().iterator();
        Iterator<String> objectsNames = objects.keySet().iterator();
        while(objectsNames.hasNext()){
            String objectName = objectsNames.next();
            Object object = objectsFiles.next();
            domain.objects.put(objectName, privileges);
            object.domains.put(domainName, privileges);
        }
        domains.put(domainName, domain);
    }
    private void removeDomain(TreeMap<String, Domain> domains, TreeMap<String, Object> objects) {
        String domainName=getDomainName();
        Iterator<Object> objectsFiles = objects.values().iterator();
        while(objectsFiles.hasNext()){
            Object object = objectsFiles.next();
            object.domains.remove(domainName);
        }
        domainCount--;
        domains.remove(domainName);
    }

    private void addObject(TreeMap<String, Domain> domains, TreeMap<String, Object> objects) {
        objectCount++;
        String objectName = getObjectName();
        Object object = new Object();
        Privileges privileges = new Privileges(false, false, false);
        Iterator<Domain> domainsFiles = domains.values().iterator();
        Iterator<String> domainsNames = domains.keySet().iterator();
        while(domainsNames.hasNext()){
            String domainName = domainsNames.next();
            Domain domain = domainsFiles.next();
            domain.objects.put(objectName, privileges);
            object.domains.put(domainName, privileges);
        }
        objects.put(objectName, object);
    }

    private void removeObject(TreeMap<String, Domain> domains, TreeMap<String, Object> objects) {
        String objectName=getObjectName();
        Iterator<Domain> domainsFiles = domains.values().iterator();
        while(domainsFiles.hasNext()){
            Domain domain = domainsFiles.next();
            domain.objects.remove(objectName);
        }
        objectCount--;
        objects.remove(objectName);
    }
    private String getDomainName() {
        if(domainCount < 0)
            domainCount=0;
        if(domainCount < 10)
            return "Domain0"+domainCount;
        else
            return "Domain"+domainCount;
    }
    private String getObjectName() {
        if(objectCount < 0)
            objectCount=0;
        if(objectCount < 10)
            return "Object0"+objectCount;
        else
            return "Object"+objectCount;
    }

    private void grantPrivilege(TreeMap<String, Domain> domains, TreeMap<String, Object> objects) {
        if(existsDomainObject(domains, objects)){
                setPrivilage(domains, objects,true);
        }
    }

    private void setPrivilage(TreeMap<String, Domain> domains, TreeMap<String, Object> objects,boolean grantRevoke) {
        Scanner reader = new Scanner(System.in);
        Privileges privileges;
        printPrivileges();
        System.out.print("Escoja privilegio: ");
        int option;
        Domain domain = null;
        Object object = null;
        try{
            option = reader.nextInt();
            domain = domains.get(dName);
            object = objects.get(oName);
        }catch(Exception e){
            reader.next();
            option=0;
        }
        if(option == 1){
            privileges =  domain.objects.get(oName);
            privileges.setRead(grantRevoke);
            domain.objects.put(oName, privileges);
            object.domains.put(dName, privileges);
        }
        else if(option == 2){
            privileges =  domain.objects.get(oName);
            privileges.setWrite(grantRevoke);
            domain.objects.put(oName, privileges);
            object.domains.put(dName, privileges);
        }
        else if(option == 3){
            privileges =  domain.objects.get(oName);
            privileges.setExecute(grantRevoke);
            domain.objects.put(oName, privileges);
            object.domains.put(dName, privileges);
        }
        else{
            System.out.println();
            System.out.println("Opcion invalida!");
        }
    }
    
    private boolean existsDomainObject(TreeMap<String, Domain> domains, TreeMap<String, Object> objects) {
        Scanner reader = new Scanner(System.in);
        System.out.print("Ingrese nombre de dominio: ");
        dName = reader.next();
        if(domains.containsKey(dName)){
            System.out.print("Ingrese nombre de objeto: ");
            oName = reader.next();
            if(objects.containsKey(oName)){
                return true;
            }else{
                System.out.println("Objeto no existe!");
                return false;
            }   
        }else{
            System.out.println("Dominio no existe!");
            return false;
        }
    }

    private void revokePrivilege(TreeMap<String, Domain> domains, TreeMap<String, Object> objects) {
      if(existsDomainObject(domains, objects)){
                setPrivilage(domains, objects,false);
      }
    }

    private void executeAction(TreeMap<String, Domain> domains) {
        Scanner reader = new Scanner(System.in);
        Privileges privileges = null;
        printPrivileges();
        System.out.print("Escoja accion a ejecutar: ");
        int option;
        Domain domain = null;
        try{
            option = reader.nextInt();
            domain = domains.get(dName);
            privileges =  domain.objects.get(oName);
        }catch(Exception e){
            reader.next();
            option=0;
        }
        if(option == 1){    
            if(privileges.getRead())
                System.out.println("Si se puede realizar accion.");
            else
                System.out.println("No se puede realizar accion.");
        }
        else if(option == 2){
           if(privileges.getWrite())
                System.out.println("Si se puede realizar accion.");
            else
                System.out.println("No se puede realizar accion.");
        }
        else if(option == 3){
          if(privileges.getExecute())
                System.out.println("Si se puede realizar accion.");
            else
                System.out.println("No se puede realizar accion.");
        }
        else{
            System.out.println();
            System.out.println("Opcion invalida!");
        }
    }

    private void printPrivileges() {
        System.out.println("1. Leer");
        System.out.println("2. Escribir");
        System.out.println("3. Ejecutar");
    }

    
    
}
