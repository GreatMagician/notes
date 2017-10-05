package notes.dao;

import notes.model.Notes;

/**
 * Управление потоками работы с бд
 */
public class ManagerBD {

    public static void initDB(){
        new Thread(){
            @Override
            public void run() {
                bd.createTable();
            }
        }.start();
    }

    public static void save(final Notes notes){
        new Thread(){
            @Override
            public void run() {
                try {
                    bd.save(notes);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }.start();
    }

    public static void delete(final Notes notes){
        new Thread(){
            @Override
            public void run() {
                try {
                    bd.delete(notes);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }.start();
    }

}
