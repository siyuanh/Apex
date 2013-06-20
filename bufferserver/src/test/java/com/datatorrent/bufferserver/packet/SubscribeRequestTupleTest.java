/*
 *  Copyright (c) 2012-2013 Malhar, Inc.
 *  All Rights Reserved.
 */
package com.datatorrent.bufferserver.packet;

import java.util.ArrayList;
import java.util.Collection;
import static org.junit.Assert.*;
import org.testng.Assert;
import org.testng.annotations.Test;

import com.datatorrent.bufferserver.packet.SubscribeRequestTuple;
import com.datatorrent.bufferserver.packet.Tuple;

/**
 *
 * @author Chetan Narsude <chetan@malhar-inc.com>
 */
public class SubscribeRequestTupleTest
{
  public SubscribeRequestTupleTest()
  {
  }

  @Test
  public void testGetSerializedRequest()
  {
    String id = "SubscriberId";
    String down_type = "SubscriberId/StreamType";
    String upstream_id = "PublisherId";
    int mask = 7;
    ArrayList<Integer> partitions = new ArrayList<Integer>();
    partitions.add(5);
    long startingWindowId = 0xcafebabe00000078L;
    byte[] serial = SubscribeRequestTuple.getSerializedRequest(null, id, down_type, upstream_id, mask, partitions, startingWindowId);
    SubscribeRequestTuple tuple = (SubscribeRequestTuple)Tuple.getTuple(serial, 0, serial.length);
    Assert.assertEquals(tuple.getIdentifier(), id, "Identifier");
    Assert.assertEquals(tuple.getStreamType(), down_type, "UpstreamType");
    Assert.assertEquals(tuple.getUpstreamIdentifier(), upstream_id, "UpstreamId");
    Assert.assertEquals(tuple.getMask(), mask, "Mask");

    int[] parts = tuple.getPartitions();
    Assert.assertTrue(parts != null && parts.length == 1 && parts[0] == 5);

    Assert.assertEquals(Long.toHexString((long)tuple.getBaseSeconds() << 32 | tuple.getWindowId()), Long.toHexString(startingWindowId), "Window");
  }

}