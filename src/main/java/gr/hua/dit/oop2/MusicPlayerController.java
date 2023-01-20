package gr.hua.dit.oop2;

import com.mpatric.mp3agic.InvalidDataException;
import com.mpatric.mp3agic.UnsupportedTagException;
import gr.hua.dit.oop2.musicplayer.PlayerException;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.*;
import java.util.ArrayList;
import java.util.logging.Level;

public class MusicPlayerController implements ActionListener {
     static ArrayList<String> songs = new ArrayList<String>();
    private final MusicPlayerModel model;
    private MusicPlayerView view;

    public MusicPlayerController(MusicPlayerModel model) {
        this.model = model;
    }

    public void setSongsInFolder(String folder, MusicPlayerView view) throws IOException {
        File dir = new File(folder);
        String path = dir.getParent();
        DefaultListModel<String> model = new DefaultListModel<>();
        if (dir.toString().endsWith(".m3u")) {
            Reader in = new FileReader(dir);
            BufferedReader in2 = new BufferedReader(in);
            String s = in2.readLine();
            while (s != null) {
                if (s.contains("#")) {
                    s = in2.readLine();
                } else if (s.contains("file:")) {
                    System.out.println(s.substring(7));
                    songs.add(s.substring(7));
                    model.addElement(s.substring(s.lastIndexOf("/") + 1));
                    s = in2.readLine();
                } else if (s.contains("\\")) {
                    System.out.println(s);
                    songs.add(s);
                    model.addElement(s);
                    s = in2.readLine();
                } else {
                    songs.add(path + "/" + s);
                    model.addElement(s);
                    s = in2.readLine();
                }
            }
        } else {
            File[] files = dir.listFiles();
            assert files != null;
            for (File file : files) {
                if (file.toString().endsWith(".mp3")) {
                    songs.add(file.toString());
                    model.addElement(file.getName());
                } else if (file.toString().endsWith(".m3u")) {
                    Reader in = new FileReader(file);
                    BufferedReader in2 = new BufferedReader(in);
                    String s = in2.readLine();
                    while (s != null) {
                        if (s.contains("#")) {
                            s = in2.readLine();
                        } else if (s.contains("file:")) {
                            System.out.println(s.substring(7));
                            songs.add(s.substring(7));
                            model.addElement(s.substring(s.lastIndexOf("/") + 1));
                            s = in2.readLine();
                        } else if (s.contains("\\")) {
                            System.out.println(s);
                            songs.add(s);
                            model.addElement(s);
                            s = in2.readLine();
                        } else {
                            songs.add(folder + "/" + s);
                            model.addElement(s);
                            s = in2.readLine();
                        }
                    }
                }
            }
        }
        view.getSongList().setModel(model);

    }
    public void setView(MusicPlayerView view) {
        this.view = view;
        view.getPlayButton().addActionListener(this);
        view.getPauseButton().addActionListener(this);
        view.getNextButton().addActionListener(this);
        view.getPrevButton().addActionListener(this);
        view.getStopButton().addActionListener(this);
        view.getShuffleButton().addActionListener(this);
        view.getRepeatButton().addActionListener(this);
    }

    @Override
    public void actionPerformed(ActionEvent e) {

        if (e.getSource() == view.getPlayButton()) {
            try {
                model.setStopPressed(false);
                view.setPlaying(true);
                model.playSong(songs);
            } catch (FileNotFoundException ex) {
                //org.hua.LogHandler.writeToLogNoThread(Level.SEVERE,"RuntimeException");
                throw new RuntimeException(ex);
            } catch (IOException | PlayerException | InterruptedException | InvalidDataException |
                     UnsupportedTagException ex) {
                throw new RuntimeException(ex);
            }
        } else if (e.getSource() == view.getPauseButton()) {
            view.setPlaying(false);
            model.pause();
            //org.hua.LogHandler.writeToLogNoThread(Level.INFO,"Model paused");
        } else if (e.getSource() == view.getStopButton()) {
            view.setPlaying(false);
            model.setStopPressed(true);
            model.stop();
            //org.hua.LogHandler.writeToLogNoThread(Level.INFO,"Model stopped");
        } else if (e.getSource() == view.getNextButton()) {
            try {
                model.setStopPressed(false);
                model.setNextPrevClick(true);
                model.next(songs);
            } catch (PlayerException | InterruptedException | FileNotFoundException ex) {
                org.hua.LogHandler.writeToLogNoThread(Level.SEVERE,"RuntimeException");
                throw new RuntimeException(ex);
            } catch (InvalidDataException | UnsupportedTagException | IOException ex) {
                throw new RuntimeException(ex);
            }
        } else if (e.getSource() == view.getPrevButton()) {
            try {
                model.setStopPressed(false);
                model.setNextPrevClick(true);
                model.prev(songs);
            } catch (InterruptedException | PlayerException | IOException | InvalidDataException |
                     UnsupportedTagException ex) {
                org.hua.LogHandler.writeToLogNoThread(Level.SEVERE,"RuntimeException");
                throw new RuntimeException(ex);
            }
        } else if (e.getSource() == view.getShuffleButton()) {
            if(model.isShuffled()){
                view.setShuffleOn(false);
                model.setShuffled(false);
                org.hua.LogHandler.writeToLogNoThread(Level.INFO,"Songs are in order");

            } else {
                view.setShuffleOn(true);
                model.setShuffled(true);
                org.hua.LogHandler.writeToLogNoThread(Level.INFO,"Songs are shuffled");
            }
        } else if (e.getSource() == view.getRepeatButton()) {
            if(model.isRepeat()){
                view.setRepeatOn(false);
                model.setRepeat(false);
                org.hua.LogHandler.writeToLogNoThread(Level.INFO,"Songs are in order");
            }
            else{
                view.setRepeatOn(true);
                model.setRepeat(true);
                org.hua.LogHandler.writeToLogNoThread(Level.INFO,"The song is looped");

            }
        }
    }
}

