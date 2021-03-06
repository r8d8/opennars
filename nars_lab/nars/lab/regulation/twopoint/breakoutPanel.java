/*
 * Here comes the text of your license
 * Each line should be prefixed with  * 
 */
package nars.lab.regulation.twopoint;
 
//TODO: Integrate ideas from [23:39] <sseehh_> patham9,  here's code https://gist.github.com/automenta/569bd8694a789a5d9490 i'm done for now

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JPanel;
import nars.util.EventEmitter;
import nars.util.Events.CycleEnd;
import nars.storage.Memory;
import nars.NAR;
import nars.config.Plugins;
import nars.entity.Task;
import nars.gui.NARSwing;
import nars.language.Term;
import nars.operator.Operation;
import nars.operator.Operator;
 
/**
 *
 * @author patrick.hammer
 */
public class breakoutPanel extends JPanel {
 
    final int feedbackCycles = 100;
    
    int movement = 0;
    int lastMovement = 0;
 
    public class move extends Operator {
 
        public move() {
            super("^move");
        }
 
        @Override
        protected List<Task> execute(Operation operation, Term[] args, Memory memory) {

            if (args.length == 2 || args.length==3) { //left, self
                prevy.clear();
                prevx.clear();
                prevsy.clear();
                prevy.add(y);
                prevx.add(x);
                prevsy.add(setpoint);
                
                long now = nar.time();
               /* if (now - lastMovementAt < minCyclesPerMovement) {
                    moving();
                    return null;
                }*/
                
                if (args[0].toString().equals("left")) {
                    x -= 10;
                    if (x <= setpoint) {
                        System.out.println("BAD:\n" + operation.getTask().getExplanation());
                        bad();
                    } else {
                        //good();
                    }
                }
                if (args[0].toString().equals("right")) {
                    x += 10;
                    if (x >= setpoint) {
                        System.out.println("BAD:\n" + operation.getTask().getExplanation());
                        bad();
                    } else {
                        //good();
                    }
                }
                
                movement++;
               // lastMovementAt = now;
                
            }
            return null;
        }
    }
    
    public void beGood() {
        nar.addInput("<SELF --> [good]>!");
     //   nar.addInput("<SELF --> [bad]>! %0%");
    }
    
    public void moving() {
      //  nar.addInput("<SELF --> [moving]>. :|:");
    }
    
    public void beGoodNow() {
        nar.addInput("<SELF --> [good]>! :|:");
    }
 
    public void good() {
        nar.addInput("<SELF --> [good]>. :|: %1.00;0.90%");
    }
    
    public void bad() {
        nar.addInput("<SELF --> [good]>. :|: %0.00;0.90%");
    }
 
    public void target(String direction) {
        if(direction.equals("left1")) {
            nar.addInput("<target --> left1>. :|:");  
        } else {
            nar.addInput("<target --> right1>. :|:");  
        }
    }
    
    NAR nar;
 
    public breakoutPanel() {
     //   Parameters.TEMPORAL_INDUCTION_SAMPLES=0;
     //   Parameters.DERIVATION_DURABILITY_LEAK=0.1f;
       // Parameters.DERIVATION_PRIORITY_LEAK=0.1f;
       // Parameters.CURIOSITY_ALSO_ON_LOW_CONFIDENT_HIGH_PRIORITY_BELIEF = false;
        nar = new NAR();
 
        nar.addPlugin(new move());
        
       /* int k=0;
        for(PluginState s: nar.getPlugins()) {
            if(s.plugin instanceof InternalExperience) {
                nar.removePlugin(s);
                break;
            }
            k++;
        }*/
        
        nar.on(CycleEnd.class, new EventEmitter.EventObserver() {
 
            @Override
            public void event(Class event, Object[] args) {
                boolean hasMoved = (movement != lastMovement);
                lastMovement = movement;
 
                if(nar.time()%100==0) {
                    y+=vx;
                    
                    if(y>300) {
                        vx=-vx;
                    }
                    if(y<10) {
                        vx=-vx;
                    }
                    
                    setpoint+=vy;
                    
                    if(setpoint>300) {
                        vy=-vy;
                    }
                    if(setpoint<10) {
                        vy=-vy;
                    }
                    
                    prevy.clear();
                    prevx.clear();
                    prevsy.clear();
                    prevy.add(y);
                    prevx.add(x);
                    prevsy.add(setpoint);
                }
                
                if (hasMoved || nar.time() % feedbackCycles == 0) {
                    if (x == setpoint)
                        good();
                }
                
                if(nar.time()%1000==0) {
                    if (x > setpoint)
                        target("left1"); //this way it also has to learn left <-> left1         
                    else if (x < setpoint)
                        target("right1"); //and learn right <-> right1
                    else
                        target("here");
                    
                }
                if(nar.time()%10000==0) {
                    beGood();
                }
                
               /* if (hasMoved) {
                    
                }*/
                
                repaint();
            }
        });
 
        NARSwing.themeInvert();
        new NARSwing(nar);
        nar.addInput("*volume=0");
        nar.start(1);
        
        intialDesire();
 
    }
 
    static int setpoint = 80; //220; //80
    int x = 160;
    int y = 10;
    double vx=5.0;
    double vy=3.0;
 
    protected void intialDesire() {
        nar.addInput("move(left)! :|: %1.00;0.50%");
        nar.addInput("move(right)! :|: %1.00;0.50%");
    }
    
    List<Integer> prevx=new ArrayList<>();
    List<Integer> prevy=new ArrayList<>();
    
    List<Integer> prevsy=new ArrayList<>();
    
    private void doDrawing(Graphics g) {
        
       
        //nar.step(10);
        Graphics2D g2d = (Graphics2D) g;
 
        for(int i=0;i<prevx.size();i++) {
            /*g2d.setColor(Color.blue);
            g2d.fillRect(0, prevx.get(i), 10, 10);*/
            g2d.setColor(Color.red);
            g2d.fillRect(prevy.get(i), prevsy.get(i), 10, 10);
        }
        g2d.setColor(Color.blue);
        
        
        g2d.fillRect(0, x-45, 10, 100);
        g2d.setColor(Color.GREEN);
        g2d.fillRect(0, x, 10, 10);
        //g2d.drawLine(0, setpoint+5, 2000, setpoint+5);
    }
 
    @Override
    public void paintComponent(Graphics g) {
 
        super.paintComponent(g);
        doDrawing(g);
    }
}