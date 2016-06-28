/*
 * UnpitchedInstrumentsPresentFeature.java
 * Version 1.2
 *
 * Last modified on April 11, 2010.
 * McGill University
 */

package jsymbolic.features;

import java.util.LinkedList;
import javax.sound.midi.*;
import ace.datatypes.FeatureDefinition;
import jsymbolic.processing.MIDIIntermediateRepresentations;


/**
 * A feature exractor that finds which unpitched MIDI Percussion Key Map 
 * instruments are present. There is one entry for each instrument, which is set 
 * to 1.0 if there is at least one Note On in the recording corresponding to the 
 * instrument and to 0.0 if there is not.
 *
 * <p>It should be noted that only instruments 35 to 81 are included here, as 
 * they are the ones that meet the official standard. They are numbered in this 
 * array from 0 to 46.
 *
 * <p>No extracted feature values are stored in objects of this class.
 *
 * @author Cory McKay
 */
public class UnpitchedInstrumentsPresentFeature
     extends MIDIFeatureExtractor
{
     /* CONSTRUCTOR ***********************************************************/
     
     
     /**
      * Basic constructor that sets the definition and dependencies (and their
      * offsets) of this feature.
      */
     public UnpitchedInstrumentsPresentFeature()
     {
          String name = "Unpitched Instruments Present";
          String description = "Which unpitched MIDI Percussion Key Map instruments are present.\n" +
               "There is one entry for each instrument, which is set\n" +
               "to 1.0 if there is at least one Note On in the recording\n" +
               "corresponding to the instrument and to 0.0 if there is not.\n" +
               "\nIt should be noted that only instruments 35 to 81 are\n" +
               "included here, as they are the ones that meet the official\n" +
               "standard. They are numbered in this array from 0 to 46.";
          boolean is_sequential = true;
          int dimensions = 47;
          definition = new FeatureDefinition( name,
               description,
               is_sequential,
               dimensions );
          
          dependencies = null;
          
          offsets = null;
     }
     
     
     /* PUBLIC METHODS ********************************************************/
     
     
     /**
      * Extracts this feature from the given MIDI sequence given the other
      * feature values.
      *
      * <p>In the case of this feature, the other_feature_values parameters
      * are ignored.
      *
      * @param sequence			The MIDI sequence to extract the feature
      *                                 from.
      * @param sequence_info		Additional data about the MIDI sequence.
      * @param other_feature_values	The values of other features that are
      *					needed to calculate this value. The
      *					order and offsets of these features
      *					must be the same as those returned by
      *					this class's getDependencies and
      *					getDependencyOffsets methods
      *                                 respectively. The first indice indicates
      *                                 the feature/window and the second
      *                                 indicates the value.
      * @return				The extracted feature value(s).
      * @throws Exception		Throws an informative exception if the
      *					feature cannot be calculated.
      */
     public double[] extractFeature( Sequence sequence,
          MIDIIntermediateRepresentations sequence_info,
          double[][] other_feature_values )
          throws Exception
     {
          double[] result = null;
          if (sequence_info != null)
          {
               result = new double[47];
               for (int instrument = 35; instrument < 82; instrument++)
               {
                    if (sequence_info.non_pitched_instrumentation_frequencies[instrument] > 0)
                         result[instrument - 35] = 1.0;
                    else
                         result[instrument - 35] = 0.0;
               }
          }
          return result;
     }
}