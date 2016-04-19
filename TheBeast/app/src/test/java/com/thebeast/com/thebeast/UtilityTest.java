package com.thebeast.com.thebeast;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import static junit.framework.Assert.assertEquals;
import static org.mockito.Mockito.when;

/**
 * To work on unit tests, switch the Test Artifact in the Build Variants view.
 */
@RunWith(MockitoJUnitRunner.class)
public class UtilityTest {

    @Mock
    private Context mMockContext;

    @Mock
    private ConnectivityManager mConnectivityManager;

    @Mock
    private NetworkInfo mNetworkInfo;

    @Test
    public void testGetTimeHrMin() throws Exception {
        String expected = "5:34 AM";
        String actual = Utility.getTime(5, 34);
        assertEquals(expected, actual);
    }

    @Test
    public void testGetTimeHrMinPM() throws Exception {
        String expected = "5:34 PM";
        String actual = Utility.getTime(17, 34);
        assertEquals(expected, actual);
    }

    @Test
    public void testGetTimeHrMinWith0() throws Exception {
        String expected = "5:04 AM";
        String actual = Utility.getTime(5, 4);
        assertEquals(expected, actual);
    }

    @Test
    public void testGetTimeHrMinPMWith0() throws Exception {
        String expected = "5:04 PM";
        String actual = Utility.getTime(17, 4);
        assertEquals(expected, actual);
    }

    @Test
    public void testGetTimeHrMin12Am() throws Exception {
        String expected = "12:34 AM";
        String actual = Utility.getTime(24, 34);
        assertEquals(expected, actual);
    }

    @Test
    public void testGetTimeMin() throws Exception {
        String expected = "5:34 AM";
        String actual = Utility.getTime(334);
        assertEquals(expected, actual);
    }

    @Test
    public void testGetTimeMinPM() throws Exception {
        String expected = "5:34 PM";
        String actual = Utility.getTime(1054);
        assertEquals(expected, actual);
    }

    @Test
    public void testGetMin() throws Exception {
        int expected = 334;
        int actual = Utility.getMins(5, 34);
        assertEquals(expected, actual);
    }

    @Test
    public void testGetMinPM() throws Exception {
        int expected = 1054;
        int actual = Utility.getMins(17, 34);
        assertEquals(expected, actual);
    }

    @Test
    public void testIsConnectedTrue() throws Exception {
        when(mMockContext.getSystemService(Context.CONNECTIVITY_SERVICE)).thenReturn(mConnectivityManager);
        when(mConnectivityManager.getActiveNetworkInfo()).thenReturn(mNetworkInfo);
        when(mNetworkInfo.isConnected()).thenReturn(true);

        boolean expected = true;
        boolean actual = Utility.isNetworkAvailable(mMockContext);
        assertEquals(expected, actual);
    }

    @Test
    public void testIsConnectedFalse() throws Exception {
        when(mMockContext.getSystemService(Context.CONNECTIVITY_SERVICE)).thenReturn(mConnectivityManager);
        when(mConnectivityManager.getActiveNetworkInfo()).thenReturn(mNetworkInfo);
        when(mNetworkInfo.isConnected()).thenReturn(false);

        boolean expected = false;
        boolean actual = Utility.isNetworkAvailable(mMockContext);
        assertEquals(expected, actual);
    }
}