/*
 * PitchClassDistributionFeature.java
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
 * A feature exractor that finds a feature array with 12 entries where the first
 * holds the frequency of the bin of the pitch class histogram with the highest
 * frequency, and the following entries holding the successive bins of the
 * histogram, wrapping around if necessary.
 *
 * <p>No extracted feature values are stored in objects of this class.
 *
 * @author Cory McKay
 */
public class PitchClassDistributionFeature
     extends MIDIFeatureExtractor
{
     /* CONSTRUCTOR ***********************************************************/
     
     
     /**
      * Basic constructor that sets the definition and dependencies (and their
      * offsets) of this feature.
      */
     public PitchClassDistributionFeature()
     {
          String name = "Pitch Class Distribution";
          String description = "A feature array with 12 entries where the first holds the\n" +
               "frequency of the bin of the pitch class histogram with the\n" +
               "highest frequency, and the following entries holding the\n" +
               "successive bins of the histogram, wrapping around if necessary.";
          boolean is_sequential = true;
          int dimensions = 12;
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
               // Find index of largest
               int index = mckay.utilities.staticlibraries.MathAndStatsMethods.getIndexOfLargest(sequence_info.pitch_class_histogram);
               
               // Construct new histogram starting with largest
               result = new double[sequence_info.pitch_class_histogram.length];
               for (int i = 0; i < result.length; i++)
               {
                    result[i] = sequence_info.pitch_class_histogram[index];
                    index++;
                    
                    // Wrap around
                    if (index == result.length)
                         index = 0;
               }
          }
          return sequence_info.pitch_class_histogram;
          //return result;
     }
}