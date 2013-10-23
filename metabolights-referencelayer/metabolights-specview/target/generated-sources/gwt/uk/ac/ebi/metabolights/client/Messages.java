/*
 * EBI MetaboLights - http://www.ebi.ac.uk/metabolights
 * Cheminformatics and Metabolism group
 *
 * Last modified: 09/09/13 12:20
 * Modified by:   kenneth
 *
 * Copyright 2013 - European Bioinformatics Institute (EMBL-EBI), European Molecular Biology Laboratory, Wellcome Trust Genome Campus, Hinxton, Cambridge CB10 1SD, United Kingdom
 */

package uk.ac.ebi.metabolights.client;

/**
 * Interface to represent the messages contained in resource bundle:
 * 	/Users/conesa/Development/metabolomes-code/referencelayer/metabolights-specview/src/main/resources/uk/ac/ebi/metabolights/client/Messages.properties'.
 */
public interface Messages extends com.google.gwt.i18n.client.Messages {

  /**
   * Translated "Enter your name".
   *
   * @return translated "Enter your name"
   */
  @DefaultMessage("Enter your name")
  @Key("nameField")
  String nameField();

  /**
   * Translated "Send".
   *
   * @return translated "Send"
   */
  @DefaultMessage("Send")
  @Key("sendButton")
  String sendButton();
}
