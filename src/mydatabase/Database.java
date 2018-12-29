/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mydatabase;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.RandomAccessFile;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

/**
 *
 * @author nikolaos
 */
public class Database {

    private String name;
    private List<Entity> entities;
    private RandomAccessFile dbmsFile;
    private RandomAccessFile databaseFile;

    public Database() {
    }

    public Database(String name, List<Entity> entities, RandomAccessFile dbmsFile, RandomAccessFile databaseFile) {
        this.name = name;
        this.entities = entities;
        this.dbmsFile = dbmsFile;
        this.databaseFile = databaseFile;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<Entity> getEntities() {
        return entities;
    }

    public void setEntities(List<Entity> entities) {
        this.entities = entities;
    }

    public RandomAccessFile getDbmsFile() {
        return dbmsFile;
    }

    public void setDbmsFile(RandomAccessFile dbmsFile) {
        this.dbmsFile = dbmsFile;
    }

    public RandomAccessFile getDatabaseFile() {
        return databaseFile;
    }

    public void setDatabaseFile(RandomAccessFile databaseFile) {
        this.databaseFile = databaseFile;
    }

    public void databaseMenu() throws IOException, ClassNotFoundException {
        int answer = 0;
        Scanner sc = new Scanner(System.in);
        while (true) {
            System.out.println("Welcome to my DATABASE Program");
            System.out.println("----------Main Menu-----------");
            System.out.println("     1. Create Database");
            System.out.println("     2. Explore Database");
            System.out.println("     3. Modify Database");
            System.out.println("     4. Delete Database");
            System.out.println("     5. Exit");
            System.out.println("------------------------------");
            System.out.print("Make a selection (1-5): ");

            boolean isSelection = sc.hasNextInt(); // Is it number or letter?

            if (isSelection) {
                answer = sc.nextInt();
                switch (answer) {
                    case 1:
                        System.out.println("Navigating to Database creation...");
                        createDatabase();
                        createEntities();
                        break;
                    case 2:
                        System.out.println("Navigating to exploring a Database...");
                        exploreDatabase();
                        break;
                    case 3:
                        System.out.println("Navigating to modifying a Database...");
                        modifyDatabase();
                        createEntities();
                        break;
                    case 4:
                        System.out.println("Navigating to deleting a Database...");
                        deleteDatabase();
                        break;
                    case 5:
                        System.out.println("Exiting...");
                        System.exit(0);
                    default:
                        System.out.println("That was not a number between 1 and 5! Try again!");
                        break;
                }
            } else {
                sc.next();
                System.out.println("That was not a number! Try Again");
            }
        }
    }

    private void createDatabase() {
        Scanner sc = new Scanner(System.in);
        System.out.println("Enter the name of your new database (Q to quit):");
        String answer = sc.next();
        if (!answer.equalsIgnoreCase("Q")) {
            this.name = answer;
            if (new File("." + answer).mkdirs()) {
                String pathDbmsName = "." + answer + "/" + answer + "sys.db";
                String pathDbName = "." + answer + "/" + answer + ".db";
                try {
                    this.dbmsFile = new RandomAccessFile(pathDbmsName, "rwd");
                    this.databaseFile = new RandomAccessFile(pathDbName, "rwd");
                    this.dbmsFile.close();
                    this.databaseFile.close();
                } catch (FileNotFoundException ex) {
                    System.out.println("Could not create Database files. Try again...");
                } catch (IOException ex) {
                    System.out.println("Could not create Database files. Try again...");
                }
                System.out.println("Database " + answer + " successfully created!");
            } else {
                System.out.println("Could not create Database files. Try again...");
            }
        } else {
            System.out.println("Quitting from \'Create Database\'...");
        }
    }

    public boolean createDatabaseGUI(String dbName) {
        boolean answer = false;
        if (new File("." + dbName).mkdirs()) {
            String pathDbmsName = "." + dbName + "/" + dbName + "sys.db";
            String pathDbName = "." + dbName + "/" + dbName + ".db";
            try {
                this.dbmsFile = new RandomAccessFile(pathDbmsName, "rwd");
                this.databaseFile = new RandomAccessFile(pathDbName, "rwd");
                this.dbmsFile.close();
                this.databaseFile.close();
            } catch (FileNotFoundException ex) {
                System.out.println("Could not create Database files. Try again...");
            } catch (IOException ex) {
                System.out.println("Could not create Database files. Try again...");
            }
            System.out.println("Database " + dbName + " successfully created!");
            answer = true;
        } else {
            System.out.println("Could not create Database files. Try again...");
        }
        return answer;
    }

    private void createEntities() throws FileNotFoundException, IOException {
        this.entities = new ArrayList();
        while (true) {
            System.out.println("---Entity Creation---");
            System.out.print("Enter an entity name: ");
            Scanner ent = new Scanner(System.in);
            String entityName = ent.nextLine();

            System.out.println("Now create attributes for entity " + entityName + ":");
            List<Attribute> attrList = new ArrayList();
            while (true) {
                Scanner attr = new Scanner(System.in);

                System.out.print("Enter attribute name:");
                String attributeName = attr.nextLine();
                System.out.print("Enter attribute data type (Number, Varchar, Boolean, Date, other entity):");
                String attributeDataType = attr.nextLine();
                System.out.print("Enter attribute data type size:");
                Integer attributeDataTypeSize = attr.nextInt();
                attrList.add(new Attribute(attributeName, new DataType(attributeDataType), attributeDataTypeSize));

                System.out.println("Would like more attributes?(Y/N)");
                Scanner ans = new Scanner(System.in);
                if (!ans.hasNext("Y")) {
                    break;
                }
            }

            Entity entity = new Entity(entityName, attrList);
            this.entities.add(entity);
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            ObjectOutputStream ooso = new ObjectOutputStream(baos);
            ooso.writeObject(entity);
            ooso.flush();
            String pathDbmsName = "." + this.name + "/" + this.name + "sys.db";
            this.dbmsFile = new RandomAccessFile(pathDbmsName, "rwd");
            this.dbmsFile.seek(this.dbmsFile.length());// Go to end of file
            this.dbmsFile.write(baos.toByteArray()); // Add entity in byte form to the end of file
            System.out.println("Entity " + entity.getName() + " successfully created!");

            System.out.println("And now store data...");
            String pathDbName = "." + this.name + "/" + this.name + ".db";
            this.databaseFile = new RandomAccessFile(pathDbName, "rwd");
            storeRecords(entity);

            System.out.println("Would you need more entities?(Y/N)");
            Scanner answer = new Scanner(System.in);
            if (!answer.hasNext("Y")) {
                break;
            }
        }
        try {
            this.dbmsFile.close(); // And safely close it
            System.out.println("Succesfully stored entities");
        } catch (IOException ex) {
            System.out.println("Database error: could not close system file");
        }
    }

    public boolean createEntityGUI(String databaseName, String entityName, List<Attribute> attrList) {
        boolean answer = false;
        Entity entity = new Entity(entityName, attrList);
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        ObjectOutputStream ooso;
        try {
            ooso = new ObjectOutputStream(baos);
            ooso.writeObject(entity);
            ooso.flush();
            String pathDbmsName = "." + databaseName + "/" + databaseName + "sys.db";
            RandomAccessFile dbmsFile = new RandomAccessFile(pathDbmsName, "rwd");
            dbmsFile.seek(dbmsFile.length());// Go to end of file
            dbmsFile.write(baos.toByteArray()); // Add entity in byte form to the end of file
            System.out.println("Entity " + entity.getName() + " successfully created!");
            answer = true;
        } catch (IOException ex) {
            System.out.println("Entity " + entity.getName() + " COULD NOT be created!");
        }

        return answer;
    }

    private void storeRecords(Entity entity) throws IOException {
        LocalDate today = LocalDate.now();
        System.out.println("Store record data for " + entity.getName());
        while (true) {
            List<Attribute> attributeList = entity.getAttributes();
            List<Object> record = new ArrayList();
            for (Attribute attribute : attributeList) {
                switch (attribute.getDataType().getName()) {
                    case "Number":
                        Scanner scn = new Scanner(System.in);
                        System.out.print(" " + attribute.getName() + ": ");
                        Long number = scn.nextLong();
                        record.add(number);
                        break;
                    case "Varchar":
                        Scanner scv = new Scanner(System.in);
                        System.out.print(" " + attribute.getName() + ": ");
                        String varchar = scv.nextLine();
                        record.add(varchar);
                        break;
                    case "Boolean":
                        Scanner scb = new Scanner(System.in);
                        System.out.print(" " + attribute.getName() + ": ");
                        Boolean bool = scb.nextBoolean();
                        record.add(bool);
                        break;
                    case "Date":
                        Scanner scd = new Scanner(System.in);
                        System.out.println(" " + attribute.getName() + ": ");
                        System.out.print("Enter Year:");
                        int year = scd.nextInt();
                        System.out.print("Enter month:");
                        int month = scd.nextInt();
                        System.out.print("Enter day: ");
                        int day = scd.nextInt();
                        LocalDate date = LocalDate.of(year, month, day);
                        record.add(date);
                        break;
                    default:
                        Scanner scf = new Scanner(System.in);
                        System.out.print(" " + attribute.getName() + ": ");
                        Object obj = new Object();
                        record.add(obj);
                        break;
                }
            }
            EntityData entityData = new EntityData(entity, today, record, 0);
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            ObjectOutputStream ooso = new ObjectOutputStream(baos);
            ooso.writeObject(entityData);
            ooso.flush();
            this.databaseFile.seek(this.databaseFile.length());// Go to end of file
            this.databaseFile.write(baos.toByteArray()); // Add entity data in byte form to the end of file
            baos.close();
            System.out.println("Succesfully stored " + entityData.toString());

            System.out.println("Do you wish to store more records?(Y/N)");
            Scanner sc = new Scanner(System.in);
            if (sc.next().equalsIgnoreCase("N")) {
                this.databaseFile.close();
                break;
            }
        }
    }

    public boolean storeEntityRecordGUI(String dbName, Entity entity, Object[] tuple, int insertUpdateDeleteMode) {
        boolean answer = false;
        String pathDbName = "." + dbName + "/" + dbName + ".db";
        LocalDate today = LocalDate.now();
        List<Attribute> attributeList = entity.getAttributes();
        List<Object> record = Arrays.asList(tuple);
        for (Attribute attribute : attributeList) {
            int entityAttributeIndex = attributeList.indexOf(attribute);
            switch (attribute.getDataType().getName()) {
                case "Number":
                    Long number;
                    if (tuple[entityAttributeIndex] instanceof String) {
                        number = Long.parseLong((String) tuple[entityAttributeIndex]);
                    } else {
                        number = (Long) tuple[entityAttributeIndex];
                    }
                    record.set(entityAttributeIndex, number);
                    break;
                case "Varchar":
                    String string = (String) tuple[entityAttributeIndex];
                    record.set(entityAttributeIndex, string);
                    break;
                case "Boolean":
                    Boolean bool;
                    if (tuple[entityAttributeIndex] instanceof String) {
                        bool = Boolean.parseBoolean((String) tuple[entityAttributeIndex]);
                    } else {
                        bool = (Boolean) tuple[entityAttributeIndex];
                    }
                    record.set(entityAttributeIndex, bool);
                    break;
                case "Date":
                    LocalDate date;
                    if (tuple[entityAttributeIndex] instanceof String) {
                        date = LocalDate.parse((String) tuple[entityAttributeIndex]);
                    } else {
                        date = (LocalDate) tuple[entityAttributeIndex];
                    }
                    record.set(entityAttributeIndex, date);
                    break;
                default:
                    Object obj = tuple[entityAttributeIndex];
                    record.set(entityAttributeIndex, obj);
                    break;
            }
        }

        try {
            RandomAccessFile dbFile = new RandomAccessFile(pathDbName, "rw");
            if (insertUpdateDeleteMode == 1) {
                System.out.println("INSERTING");
                EntityData entityData = new EntityData(entity, today, record, 0);
                ByteArrayOutputStream baos = new ByteArrayOutputStream();
                ObjectOutputStream ooso = new ObjectOutputStream(baos);
                ooso.writeObject(entityData);
                ooso.flush();
                dbFile.seek(dbFile.length()); //Go at the end of file
                dbFile.write(baos.toByteArray()); // Add entity data in byte form to the end of file
                baos.close();
                ooso.close();
            } else if (insertUpdateDeleteMode == 2) { // UPDATE
                System.out.println("UPDATING");
                EntityData newEntityData = new EntityData(entity, today, record, 0);
                while (dbFile.getFilePointer() != dbFile.length()) {
                    long filePointer = dbFile.getFilePointer();
                    dbFile.seek(dbFile.getFilePointer());
                    ObjectInputStream ois = new ObjectInputStream(new FileInputStream(dbFile.getFD()));
                    EntityData oldEntityData = (EntityData) ois.readObject(); //after this point file pointer goes to the next byte
                    if (oldEntityData.getEntity().getName().equals(newEntityData.getEntity().getName())
                            && oldEntityData.getDeleted() == 0
                            && oldEntityData.getRecord().get(0).equals(newEntityData.getRecord().get(0))) { //By Id
                        dbFile.seek(filePointer);
                        ByteArrayOutputStream baos = new ByteArrayOutputStream();
                        ObjectOutputStream ooso = new ObjectOutputStream(baos);
                        oldEntityData.setDeleted(1);
                        ooso.writeObject(oldEntityData);
                        ooso.flush();
                        dbFile.write(baos.toByteArray());
                        baos.close();
                        break;
                    }
                }
                ByteArrayOutputStream baos = new ByteArrayOutputStream();
                ObjectOutputStream ooso = new ObjectOutputStream(baos);
                ooso.writeObject(newEntityData);
                ooso.flush();
                dbFile.seek(dbFile.length()); //Go at the end of file
                dbFile.write(baos.toByteArray()); // Add entity data in byte form to the end of file
                baos.close();
                ooso.close();
            } else if (insertUpdateDeleteMode == 3) { // DELETE
                EntityData existingEntity = new EntityData(entity, today, record, 1);
                while (dbFile.getFilePointer() != dbFile.length()) {
                    long filePointer = dbFile.getFilePointer();
                    dbFile.seek(dbFile.getFilePointer());
                    ObjectInputStream ois = new ObjectInputStream(new FileInputStream(dbFile.getFD()));
                    EntityData oldEntityData = (EntityData) ois.readObject(); //after this point file pointer goes to the next byte
                    if (oldEntityData.getEntity().getName().equals(existingEntity.getEntity().getName())
                            && oldEntityData.getDeleted() == 0
                            && oldEntityData.getRecord().get(0).equals(existingEntity.getRecord().get(0))) { //By Id
                        dbFile.seek(filePointer);
                        ByteArrayOutputStream baos = new ByteArrayOutputStream();
                        ObjectOutputStream ooso = new ObjectOutputStream(baos);
                        oldEntityData.setDeleted(1);
                        ooso.writeObject(oldEntityData);
                        ooso.flush();
                        dbFile.write(baos.toByteArray());
                        baos.close();
                        break;
                    }
                }
            }

            dbFile.close();

            answer = true;
        } catch (FileNotFoundException ex) {
            System.out.println("FILE NOT FOUND EXCEPTION");
            ex.printStackTrace();
            answer = false;
        } catch (IOException ex) {
            System.out.println("IO EXCEPTION");
            ex.printStackTrace();
            answer = false;
        } catch (ClassNotFoundException ex) {
            System.out.println("CLASS NOT FOUND EXCEPTION");
            ex.printStackTrace();
            answer = false;
        }

        return answer;
    }

    private void exploreDatabase() throws IOException, ClassNotFoundException {
        // List the existing databases
        System.out.println("Existing Databases are:");
        File[] files = new File(".").listFiles();
        for (File f : files) {
            if (f.isDirectory() && f.isHidden()) {
                System.out.println(f.getName());
            }
        }
        Scanner sc = new Scanner(System.in);
        System.out.println("Enter the name of your database you wish to explore (Q to quit):");
        String answer = sc.next();
        if (!answer.equalsIgnoreCase("Q")) {
            this.name = answer;
            if (new File("." + answer).exists()) {
                String pathDbmsName = "." + answer + "/" + answer + "sys.db";

                this.dbmsFile = new RandomAccessFile(pathDbmsName, "rwd");

                System.out.println("Database " + answer + " exists and successfully opened for exploration!");

                System.out.println("Entities in database " + answer + " are the following:");
                // Reading whole file sequentially
                while (this.dbmsFile.getFilePointer() != this.dbmsFile.length()) {
                    this.dbmsFile.seek(this.dbmsFile.getFilePointer());
                    ObjectInputStream ois = new ObjectInputStream(new FileInputStream(this.dbmsFile.getFD()));
                    Entity entity = (Entity) ois.readObject();
                    System.out.print(entity.getName() + "(");
                    int cnt = 1;
                    for (Attribute attr : entity.getAttributes()) {
                        System.out.print(attr.getName());
                        if (cnt != entity.getAttributes().size()) {
                            System.out.print(",");
                        }
                        ++cnt;
                    }
                    System.out.println(")");
                    System.out.println("--- With the following record data:");
                    showRecords(entity);
                }
                this.dbmsFile.close();

            } else {
                System.out.println("Database  " + answer + " does not exist. Try again...");
            }

        } else {
            System.out.println("Quitting from \'Explore Database\'...");
        }
    }

    public List<String> fetchDatabasesGUI() {
        List<String> dbList = new ArrayList();
        File[] files = new File(".").listFiles();
        for (File f : files) {
            if (f.isDirectory() && f.isHidden()) {
                dbList.add(f.getName().replace(".", ""));
            }
        }
        return dbList;
    }

    public List<Entity> fetchDatabaseEntitiesGUI(String dbName) {
        List<Entity> entList = new ArrayList();
        String pathDbmsName = "." + dbName + "/" + dbName + "sys.db";

        try {
            RandomAccessFile raf = new RandomAccessFile(pathDbmsName, "rwd");

            // Reading whole file sequentially
            while (raf.getFilePointer() != raf.length()) {
                raf.seek(raf.getFilePointer());
                ObjectInputStream ois = new ObjectInputStream(new FileInputStream(raf.getFD()));
                Entity entity = (Entity) ois.readObject();
                //System.out.print(entity.getName() + "(");
                int cnt = 1;
                for (Attribute attr : entity.getAttributes()) {
                    System.out.print(attr.getName());
                    if (cnt != entity.getAttributes().size()) {
                        System.out.print(",");
                    }
                    ++cnt;
                }
                System.out.println(")");
                entList.add(entity);
            }
            raf.close();
        } catch (IOException ex) {

        } catch (ClassNotFoundException ex) {

        }

        return entList;
    }

    public Entity fetchDatabaseEntityByNameGUI(String dbName, String entityName) {
        Entity ent = new Entity();
        String pathDbmsName = "." + dbName + "/" + dbName + "sys.db";

        try {
            RandomAccessFile raf = new RandomAccessFile(pathDbmsName, "rwd");

            // Reading whole file sequentially
            while (raf.getFilePointer() != raf.length()) {
                raf.seek(raf.getFilePointer());
                ObjectInputStream ois = new ObjectInputStream(new FileInputStream(raf.getFD()));
                Entity entity = (Entity) ois.readObject();
                if (entity.getName().equals(entityName)) {
                    ent = entity;
                    break;
                }
            }
            raf.close();
        } catch (IOException ex) {

        } catch (ClassNotFoundException ex) {

        }
        return ent;
    }

    private void showRecords(Entity entity) throws IOException, ClassNotFoundException {
        String pathDbName = "." + this.name + "/" + this.name + ".db";

        this.databaseFile = new RandomAccessFile(pathDbName, "rwd");

        while (this.databaseFile.getFilePointer() != this.databaseFile.length()) {
            this.databaseFile.seek(this.databaseFile.getFilePointer());
            ObjectInputStream ois = new ObjectInputStream(new FileInputStream(this.databaseFile.getFD()));
            EntityData entry = (EntityData) ois.readObject();

            if (entry.getEntity().getName().equals(entity.getName())) {
                System.out.println("--- " + entry.toString());
            }

        }
        this.databaseFile.close();
    }

    public List<EntityData> fetchDatabaseEntityDataGUI(String dbName, String entityName) {
        List<EntityData> entDataList = new ArrayList();
        String pathDbName = "." + dbName + "/" + dbName + ".db";

        RandomAccessFile raf;
        try {
            raf = new RandomAccessFile(pathDbName, "rwd");
            while (raf.getFilePointer() != raf.length()) {
                raf.seek(raf.getFilePointer());
                ObjectInputStream ois = new ObjectInputStream(new FileInputStream(raf.getFD()));
                EntityData entry = (EntityData) ois.readObject();

                if (entry.getEntity().getName().equals(entityName) && entry.getDeleted() == 0) {
                    entDataList.add(entry);
                }
            }
            raf.close();
        } catch (FileNotFoundException ex) {

        } catch (IOException ex) {

        } catch (ClassNotFoundException ex) {

        }

        return entDataList;
    }

    public boolean maintainDatabaseGUI(String dbName) {
        boolean answer = false;
        List<EntityData> entDataList = new ArrayList();
        String pathDbName = "." + dbName + "/" + dbName + ".db";

        RandomAccessFile raf;
        try {
            raf = new RandomAccessFile(pathDbName, "rwd");
            System.out.println(dbName + ": PREVIOUS BYTES = " + raf.length());
            while (raf.getFilePointer() != raf.length()) {
                raf.seek(raf.getFilePointer());
                ObjectInputStream ois = new ObjectInputStream(new FileInputStream(raf.getFD()));
                EntityData entry = (EntityData) ois.readObject();
                if(entry.getDeleted() == 0){
                    entDataList.add(entry);
                }
            }
            raf.close();
            File f = new File(pathDbName);
            if(f.delete()){
                System.out.println("DB FILE SUCCESFULLY DELETED!");
            }
            raf = new RandomAccessFile(pathDbName, "rwd");
            for (EntityData ed : entDataList) {
                ByteArrayOutputStream baos = new ByteArrayOutputStream();
                ObjectOutputStream ooso = new ObjectOutputStream(baos);
                ooso.writeObject(ed);
                ooso.flush();
                raf.seek(raf.length()); //Go at the end of file
                raf.write(baos.toByteArray()); // Add entity data in byte form to the end of file
                baos.close();
                ooso.close();
            }
            System.out.println(dbName + ": BYTES AFTER MAINTENANCE = " + raf.length());
            raf.close();
            answer = true;
        } catch (FileNotFoundException ex) {
            answer = false;
        } catch (IOException ex) {
            answer = false;
        } catch (ClassNotFoundException ex) {
            answer = false;
        }

        return answer;
    }

    private void modifyDatabase() throws FileNotFoundException {
        // List the existing databases
        System.out
                .println("Existing Databases are:");
        File[] files
                = new File(".").listFiles();

        for (File f
                : files) {
            if (f
                    .isDirectory() && f
                            .isHidden()) {
                System.out
                        .println(f
                                .getName());

            }
        }
        Scanner sc = new Scanner(System.in);
        System.out.println("Enter the name of your database you wish to explore (Q to quit):");
        String answer = sc.next();

        if (!answer
                .equalsIgnoreCase("Q")) {
            this.name
                    = answer;

            if (new File("." + answer
            ).exists()) {
                String pathDbmsName
                        = "." + answer
                        + "/" + answer
                        + "sys.db";
                String pathDbName
                        = "." + answer
                        + "/" + answer
                        + ".db";

                this.dbmsFile
                        = new RandomAccessFile(pathDbmsName,
                                "rwd");

                this.databaseFile
                        = new RandomAccessFile(pathDbName,
                                "rwd");

                System.out
                        .println("Database " + answer
                                + " exists and successfully opened for modifications!");

            }
        }
    }

    private void deleteDatabase() {
        // List the existing databases
        System.out.println("Existing Databases are:");
        File[] files = new File(".").listFiles();

        for (File f : files) {
            if (f.isDirectory() && f.isHidden()) {
                System.out.println(f.getName());
            }
        }
        Scanner sc = new Scanner(System.in);
        System.out.println("Enter the name of your database you wish to delete. (Q to quit):");
        String answer = sc.next();

        if (!answer.equalsIgnoreCase("Q")) {
            File f = new File("." + answer);

            if (f.exists() && f.isDirectory()) {
                File[] dirFiles = f.listFiles();

                if (dirFiles[0].delete() && dirFiles[1].delete() && f.delete()) {
                    System.out.println("Database " + answer + " deleted successfully!");
                }
            }
        }
    }

    public boolean deleteDatabaseGUI(String dbName) {
        boolean deleted = false;
        File f = new File("." + dbName);

        if (f.exists() && f.isDirectory()) {
            File[] dirFiles = f.listFiles();

            if (dirFiles[0].delete() && dirFiles[1].delete() && f.delete()) {
                System.out.println("Database " + dbName + " deleted successfully!");
                deleted = true;
            }
        }
        return deleted;
    }

}
