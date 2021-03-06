/*
 * Copyright (C) 2014 tc
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */

package nars.lab.tictactoe;

import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;
import nars.storage.Memory;
import nars.NAR;
import nars.config.Plugins;
import nars.entity.Task;
import nars.gui.NARSwing;
import nars.language.Term;
import nars.operator.Operation;
import nars.operator.Operator;

/**
 * Old version, broken
 * @author tc
 */
class play extends javax.swing.JFrame {

    /**
     * Creates new form play
     */
    private static NAR nar;
    public play() {
        
        
        nar.memory.addOperator(new AddO("^addO"));
        (nar.param).noiseLevel.set(0);
        
        initComponents();
        addStartKnowledge();
        nar.addInput("<game --> reset>. :|:");
        
        try {
            Thread.sleep(500);
        } catch (InterruptedException ex) {
            Logger.getLogger(play.class.getName()).log(Level.SEVERE, null, ex);
        }
        NARSwing.themeInvert();
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(play.class.getName()).log(Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            Logger.getLogger(play.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            Logger.getLogger(play.class.getName()).log(Level.SEVERE, null, ex);
        } catch (UnsupportedLookAndFeelException ex) {
            Logger.getLogger(play.class.getName()).log(Level.SEVERE, null, ex);
        }
        new NARSwing(nar);
        narstart();
    }
    
    int[] field=new int[]{ 0,0,0,
                           0,0,0,
                           0,0,0
    };
                            
    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jButton11 = new javax.swing.JButton();
        jPanel1 = new javax.swing.JPanel();
        jButton3 = new javax.swing.JButton();
        jButton2 = new javax.swing.JButton();
        jButton5 = new javax.swing.JButton();
        jButton8 = new javax.swing.JButton();
        jButton6 = new javax.swing.JButton();
        jButton9 = new javax.swing.JButton();
        jButton10 = new javax.swing.JButton();
        jButton7 = new javax.swing.JButton();
        jButton4 = new javax.swing.JButton();
        jButton1 = new javax.swing.JButton();
        jLabel1 = new javax.swing.JLabel();
        jButton12 = new javax.swing.JButton();

        jButton11.setText("jButton11");

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("NARTacToe");
        setBackground(new java.awt.Color(0, 0, 0));
        setResizable(false);

        jPanel1.setBackground(new java.awt.Color(0, 0, 0));

        jButton3.setBackground(new java.awt.Color(0, 0, 0));
        jButton3.setForeground(new java.awt.Color(255, 255, 255));
        jButton3.setText("_");
        jButton3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton3ActionPerformed(evt);
            }
        });

        jButton2.setBackground(new java.awt.Color(0, 0, 0));
        jButton2.setForeground(new java.awt.Color(255, 255, 255));
        jButton2.setText("_");
        jButton2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton2ActionPerformed(evt);
            }
        });

        jButton5.setBackground(new java.awt.Color(0, 0, 0));
        jButton5.setForeground(new java.awt.Color(255, 255, 255));
        jButton5.setText("_");
        jButton5.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton5ActionPerformed(evt);
            }
        });

        jButton8.setBackground(new java.awt.Color(0, 0, 0));
        jButton8.setForeground(new java.awt.Color(255, 255, 255));
        jButton8.setText("_");
        jButton8.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton8ActionPerformed(evt);
            }
        });

        jButton6.setBackground(new java.awt.Color(0, 0, 0));
        jButton6.setForeground(new java.awt.Color(255, 255, 255));
        jButton6.setText("_");
        jButton6.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton6ActionPerformed(evt);
            }
        });

        jButton9.setBackground(new java.awt.Color(0, 0, 0));
        jButton9.setForeground(new java.awt.Color(255, 255, 255));
        jButton9.setText("_");
        jButton9.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton9ActionPerformed(evt);
            }
        });

        jButton10.setBackground(new java.awt.Color(0, 0, 0));
        jButton10.setForeground(new java.awt.Color(255, 255, 255));
        jButton10.setText("_");
        jButton10.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton10ActionPerformed(evt);
            }
        });

        jButton7.setBackground(new java.awt.Color(0, 0, 0));
        jButton7.setForeground(new java.awt.Color(255, 255, 255));
        jButton7.setText("_");
        jButton7.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton7ActionPerformed(evt);
            }
        });

        jButton4.setBackground(new java.awt.Color(0, 0, 0));
        jButton4.setForeground(new java.awt.Color(255, 255, 255));
        jButton4.setText("_");
        jButton4.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton4ActionPerformed(evt);
            }
        });

        jButton1.setBackground(new java.awt.Color(0, 0, 0));
        jButton1.setForeground(new java.awt.Color(255, 255, 255));
        jButton1.setText("Reset");
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });

        jLabel1.setForeground(new java.awt.Color(255, 255, 255));
        jLabel1.setText("playing...");
        jLabel1.setToolTipText("");

        jButton12.setBackground(new java.awt.Color(0, 0, 0));
        jButton12.setForeground(new java.awt.Color(255, 255, 255));
        jButton12.setText("remind game");
        jButton12.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton12ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(jButton1, javax.swing.GroupLayout.PREFERRED_SIZE, 69, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(1, 1, 1)
                        .addComponent(jLabel1)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jButton12))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(jButton2, javax.swing.GroupLayout.PREFERRED_SIZE, 87, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jButton5, javax.swing.GroupLayout.PREFERRED_SIZE, 87, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jButton8, javax.swing.GroupLayout.PREFERRED_SIZE, 87, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(jButton3, javax.swing.GroupLayout.PREFERRED_SIZE, 87, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jButton6, javax.swing.GroupLayout.PREFERRED_SIZE, 87, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jButton9, javax.swing.GroupLayout.PREFERRED_SIZE, 87, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(jButton4, javax.swing.GroupLayout.PREFERRED_SIZE, 87, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jButton7, javax.swing.GroupLayout.PREFERRED_SIZE, 87, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jButton10, javax.swing.GroupLayout.PREFERRED_SIZE, 87, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jButton2, javax.swing.GroupLayout.PREFERRED_SIZE, 79, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jButton5, javax.swing.GroupLayout.PREFERRED_SIZE, 79, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jButton8, javax.swing.GroupLayout.PREFERRED_SIZE, 79, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jButton3, javax.swing.GroupLayout.PREFERRED_SIZE, 79, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jButton6, javax.swing.GroupLayout.PREFERRED_SIZE, 79, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jButton9, javax.swing.GroupLayout.PREFERRED_SIZE, 79, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jButton4, javax.swing.GroupLayout.PREFERRED_SIZE, 79, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jButton7, javax.swing.GroupLayout.PREFERRED_SIZE, 79, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jButton10, javax.swing.GroupLayout.PREFERRED_SIZE, 79, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jButton1)
                    .addComponent(jLabel1)
                    .addComponent(jButton12))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, 309, Short.MAX_VALUE)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    
    public class AddO extends Operator {

    public AddO(String name) {
        super(name);
    }

    @Override
    protected List<Task> execute(Operation operation, Term[] args, Memory memory) {
        //Operation content = (Operation) task.getContent();
        //Operator op = content.getOperator();
         
        boolean success=true;
        System.out.println("Executed: " + this);
        if(args[0].toString().equals("1") && field[0]==0) {
            jButton2.setText("O");
            field[0]=2;
        }
        else
        if(args[0].toString().equals("2") && field[1]==0) {
            jButton5.setText("O");
            field[1]=2;
        }
        else
        if(args[0].toString().equals("3") && field[2]==0) {
            jButton8.setText("O");
            field[2]=2;
        }
        else
        if(args[0].toString().equals("4") && field[3]==0) {
            jButton3.setText("O");
            field[3]=2;
        }
        else
        if(args[0].toString().equals("5") && field[4]==0) {
            jButton6.setText("O");
            field[4]=2;
        }
        else
        if(args[0].toString().equals("6") && field[5]==0) {
            jButton9.setText("O");
            field[5]=2;
        }
        else
        if(args[0].toString().equals("7") && field[6]==0) {
             jButton4.setText("O");
            field[6]=2;
        }
        else
        if(args[0].toString().equals("8") && field[7]==0) {
            jButton7.setText("O");
            field[7]=2;
        }
        else
        if(args[0].toString().equals("9") && field[8]==0) {
            nar.addInput("<input --> succeeded>. :|: %1.00;0.99%");
            jButton10.setText("O");
            field[8]=2;
        }
        else {
            nar.addInput("<input --> succeeded>. :|: %0.00;0.99%");
            success=false;
        }
        
        if(success) {
            enableall(true);
            check_field();
            nar.step(100); //give time to see win condition
            nar.stop();
        }
            
        //for (Term t : args) {
        //    System.out.println(" --- " + t);
       // }
        
        return null;
    }

}
    
    public boolean check_field()
    {
        int won=0; //player 1 won: won=1, nars won: won=2
        
        for(int player=1;player<=2;player++) { //up down
            for(int i=0;i<3;i++) {
                if(field[i]==player && field[i+3]==player && field[i+3+3]==player) {
                    won=player;
                }
            }
            
            for(int i=0;i<=6;i+=3) { //left right
                if(field[i]==player && field[i+1]==player && field[i+1+1]==player) {
                    won=player;
                }
            }
            
            if(field[0]==player && field[4]==player && field[8]==player) { //left diagonale
                won=player;
            }
            if(field[2]==player && field[4]==player && field[6]==player) { //right diagonale
                won=player;
            }
        }
        
        
        if(won==1) {
            this.jLabel1.setText("Player won");
            nar.addInput("<goal --> reached>. %0.0;0.99%");
        }
        if(won==2) {
            this.jLabel1.setText("NARS won");
            nar.addInput("<goal --> reached>. %1.0;0.99%");
        }
        
        return won!=0;
    }
    public void narstart() { 
       // nar.start(50, 500);
        nar.start(0);
    }
    
    boolean en=false;
    public void enableall(boolean state) {
       /* jButton2.setEnabled(state);
        jButton3.setEnabled(state);
        jButton4.setEnabled(state);
        jButton5.setEnabled(state);
        jButton6.setEnabled(state);
        jButton7.setEnabled(state);
        jButton8.setEnabled(state);
        jButton9.setEnabled(state);
        jButton10.setEnabled(state);*/
        en=state;
    }
    
    private void jButton2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton2ActionPerformed
        // TODO add your handling code here:
        if(!en || field[0]!=0) {
            return;
        }
        nar.addInput("<1 --> set>. :|:");
        jButton2.setText("X");
        field[0]=1;
        if(!check_field()) {
            narstart(); //nars turn
            enableall(false);
        }
    }//GEN-LAST:event_jButton2ActionPerformed

    private void jButton3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton3ActionPerformed
        // TODO add your handling code here:
        if(!en || field[3]!=0) {
            return;
        }
        nar.addInput("<4 --> set>. :|:");
        jButton3.setText("X");
        field[3]=1;
        check_field();
        if(!check_field()) {
            narstart(); //nars turn
            enableall(false);
        }
    }//GEN-LAST:event_jButton3ActionPerformed

    private void jButton4ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton4ActionPerformed
        // TODO add your handling code here:
        if(!en || field[6]!=0) {
            return;
        }
        nar.addInput("<7 --> set>. :|:");
        jButton4.setText("X");
        field[6]=1;
        check_field();
        if(!check_field()) {
            narstart(); //nars turn
            enableall(false);
        }
    }//GEN-LAST:event_jButton4ActionPerformed

    private void jButton5ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton5ActionPerformed
        // TODO add your handling code here:
        if(!en || field[1]!=0) {
            return;
        }
        nar.addInput("<2 --> set>. :|:");
        jButton5.setText("X");
        field[1]=1;
        check_field();
        if(!check_field()) {
            narstart(); //nars turn
            enableall(false);
        }
    }//GEN-LAST:event_jButton5ActionPerformed

    private void jButton6ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton6ActionPerformed
        // TODO add your handling code here:
        if(!en || field[4]!=0) {
            return;
        }
        nar.addInput("<5 --> set>. :|:");
        jButton6.setText("X");
        field[4]=1;
        check_field();
        if(!check_field()) {
            narstart(); //nars turn
            enableall(false);
        }
    }//GEN-LAST:event_jButton6ActionPerformed

    private void jButton7ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton7ActionPerformed
        // TODO add your handling code here:
        if(!en || field[7]!=0) {
            return;
        }
        nar.addInput("<8 --> set>. :|:");
        jButton7.setText("X");
        field[7]=1;
        check_field();
        if(!check_field()) {
            narstart(); //nars turn
            enableall(false);
        }
    }//GEN-LAST:event_jButton7ActionPerformed

    private void jButton8ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton8ActionPerformed
        // TODO add your handling code here:
        if(!en || field[2]!=0) {
            return;
        }
        nar.addInput("<3 --> set>. :|:");
        jButton8.setText("X");
        field[2]=1;
        check_field();
        if(!check_field()) {
            narstart(); //nars turn
            enableall(false);
        }
    }//GEN-LAST:event_jButton8ActionPerformed

    private void jButton9ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton9ActionPerformed
        // TODO add your handling code here:
        if(!en || field[5]!=0) {
            return;
        }
        nar.addInput("<6 --> set>. :|:");
        jButton9.setText("X");
        field[5]=1;
        check_field();
        if(!check_field()) {
            narstart(); //nars turn
            enableall(false);
        }
    }//GEN-LAST:event_jButton9ActionPerformed

    private void jButton10ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton10ActionPerformed
        // TODO add your handling code here:
        if(!en || field[8]!=0) {
            return;
        }
        nar.addInput("<9 --> set>. :|:");
        jButton10.setText("X");
        field[8]=1;
        check_field();
        if(!check_field()) {
            narstart(); //nars turn
            enableall(false);
        }
    }//GEN-LAST:event_jButton10ActionPerformed

    boolean beginner=false;
    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        // TODO add your handling code here:
        jButton2.setText("_");
        jButton3.setText("_");
        jButton4.setText("_");
        jButton5.setText("_");
        jButton6.setText("_");
        jButton7.setText("_");
        jButton8.setText("_");
        jButton9.setText("_");
        jButton10.setText("_");
        this.jLabel1.setText("playing...");
        field=new int[]{ 0,0,0,
                         0,0,0,
                         0,0,0
        };
        nar.addInput("<game --> reset>. :|:");
        addStartKnowledge();
        
        if(!beginner) {
            narstart(); //nars turn
            enableall(false);
        }
        else {
            nar.stop();
            enableall(true);
        }
        
        beginner=!beginner;
    }//GEN-LAST:event_jButton1ActionPerformed

    private void jButton12ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton12ActionPerformed
        // TODO add your handling code here:
        addStartKnowledge();
    }//GEN-LAST:event_jButton12ActionPerformed

    public void addStartKnowledge() {
        nar.addInput("<goal --> reached>! %1.0;0.99%");
        
        //nar.addInput("<(^addO,$1) =/> <input --> succeeded>>."); //usually input succeeds
        //nar.addInput("<(&/,<1 --> set>,(^addO,$1)) =/> (--,<input --> succeeded>)>."); //usually input succeeds but not when it was set by player cause overwrite is not valid
        //nar.addInput("<(&/,(^addO,$1),(^addO,$1)) =/> (--,<input --> succeeded>)>."); //also overwriting on own is not valid
        nar.addInput("<(&|,(^addO,1),<input --> succeeded>,(^addO,2),<input --> succeeded>,(^addO,3),<input --> succeeded>) =/> <goal --> reached>>.");
        nar.addInput("<(&|,(^addO,4),<input --> succeeded>,(^addO,5),<input --> succeeded>,(^addO,6),<input --> succeeded>) =/> <goal --> reached>>.");
        nar.addInput("<(&|,(^addO,7),<input --> succeeded>,(^addO,8),<input --> succeeded>,(^addO,9),<input --> succeeded>) =/> <goal --> reached>>.");
        //also with 3 in a column:
        nar.addInput("<(&|,(^addO,1),<input --> succeeded>,(^addO,4),<input --> succeeded>,(^addO,7),<input --> succeeded>) =/> <goal --> reached>>.");
        nar.addInput("<(&|,(^addO,2),<input --> succeeded>,(^addO,5),<input --> succeeded>,(^addO,8),<input --> succeeded>) =/> <goal --> reached>>.");
        nar.addInput("<(&|,(^addO,3),<input --> succeeded>,(^addO,6),<input --> succeeded>,(^addO,9),<input --> succeeded>) =/> <goal --> reached>>.");
        //and with the 2 diagonals:
        nar.addInput("<(&|,(^addO,1),<input --> succeeded>,(^addO,5),<input --> succeeded>,(^addO,9),<input --> succeeded>) =/> <goal --> reached>>.");
        nar.addInput("<(&|,(^addO,3),<input --> succeeded>,(^addO,5),<input --> succeeded>,(^addO,7),<input --> succeeded>) =/> <goal --> reached>>.");
        //
        nar.addInput("<goal --> reached>! %1.0;0.99%");
        
        /*nar.addInput("(&/,<#1 --> field>,(^addO,#1))!"); //doing something is also a goal :D
        nar.addInput("(^addO,1)! %1.0;0.7%");
        nar.addInput("(^addO,2)! %1.0;0.7%");
        nar.addInput("(^addO,3)! %1.0;0.7%");
        nar.addInput("(^addO,4)! %1.0;0.7%");
        nar.addInput("(^addO,5)! %1.0;0.7%");
        nar.addInput("(^addO,6)! %1.0;0.7%");
        nar.addInput("<1 --> field>.");
        nar.addInput("<2 --> field>.");
        nar.addInput("<3 --> field>.");
        nar.addInput("<4 --> field>.");
        nar.addInput("<5 --> field>.");
        nar.addInput("<6 --> field>.");
        nar.addInput("<7 --> field>.");
        nar.addInput("<8 --> field>.");
        nar.addInput("<9 --> field>.");*/
        nar.addInput("<input --> succeeded>!");
        
    }
    
    /**
     * @param args the command line arguments
     */
    public static void _main(String args[]) {
        
        /* Set the Nimbus look and feel */
        NARSwing.themeInvert();
        nar = new NAR();
        
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(play.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(play.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(play.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(play.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } 
        //</editor-fold>
       
        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new play().setVisible(true);
            }
        });
        
        
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButton10;
    private javax.swing.JButton jButton11;
    private javax.swing.JButton jButton12;
    private javax.swing.JButton jButton2;
    private javax.swing.JButton jButton3;
    private javax.swing.JButton jButton4;
    private javax.swing.JButton jButton5;
    private javax.swing.JButton jButton6;
    private javax.swing.JButton jButton7;
    private javax.swing.JButton jButton8;
    private javax.swing.JButton jButton9;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JPanel jPanel1;
    // End of variables declaration//GEN-END:variables
}
