package tp1.automatas;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotEquals;

import org.junit.Test;

public class CloneTest {

    private State ts1 = new State("s1", true, false);
    private State ts2 = new State("s2", false, true);
    private State ts3 = new State("s3", false, false);
    private StateSet tss = new StateSet();
    private Alphabet ta = new Alphabet();


    @Test
    public void cloneStateTest() throws CloneNotSupportedException 
    {
        State s = ts1.cloneState();

        s.setInitial(false);
        s.setName("q0");

        assertNotEquals(ts1.isInitial(), s.isInitial());
        assertNotEquals(ts1.getName(), s.getName());
        assertEquals(ts1.isFinal(), s.isFinal());

        assertFalse(ts1.equals(s));
    }

    @Test
    public void cloneStateSetTest() throws AutomatonException, CloneNotSupportedException
    {
        tss.addState(ts1);
        tss.addState(ts2);
        tss.addState(ts3);

        StateSet ss = tss.cloneSS();
        ss.deleteState("s1");
        assertNotEquals(tss.size(), ss.size());
    }

    @Test
    public void cloneAlphabetTest() throws CloneNotSupportedException
    {
        ta.addSymbol('a');
        ta.addSymbol('b');

        Alphabet a = ta.cloneAlpha();
        a.removeSymbol('b');

        assertTrue(ta.belongsTo('b'));
        assertFalse(a.belongsTo('b'));
    }

    
}
