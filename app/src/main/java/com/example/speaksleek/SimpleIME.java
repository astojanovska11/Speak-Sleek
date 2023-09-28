package com.example.speaksleek;

import android.inputmethodservice.InputMethodService;
import android.inputmethodservice.Keyboard;
import android.inputmethodservice.KeyboardView;
import android.media.AudioManager;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.ExtractedTextRequest;
import android.view.inputmethod.InputConnection;

import java.util.List;
import java.util.Properties;

import edu.stanford.nlp.pipeline.StanfordCoreNLP;

import edu.stanford.nlp.util.CoreMap;
import edu.stanford.nlp.ling.CoreAnnotations;
import edu.stanford.nlp.ling.CoreLabel;
import edu.stanford.nlp.pipeline.Annotation;



public class SimpleIME extends InputMethodService
        implements KeyboardView.OnKeyboardActionListener {

    private KeyboardView kv;
    private Keyboard keyboard;

    private boolean caps = false;

    private StanfordCoreNLP pipeline;

    @Override
    public View onCreateInputView() {
        // Initialize Stanford NLP pipeline
        Properties props = new Properties();
        props.setProperty("annotators", "tokenize, ssplit, pos, lemma");
        pipeline = new StanfordCoreNLP(props);


        kv = (KeyboardView)getLayoutInflater().inflate(R.layout.keyboard, null);
        keyboard = new Keyboard(this, R.xml.qwerty);
        kv.setKeyboard(keyboard);
        kv.setOnKeyboardActionListener(this);
        return kv;
    }
    private void playClick(int keyCode){
        AudioManager am = (AudioManager)getSystemService(AUDIO_SERVICE);
        switch(keyCode){
            case 32:
                am.playSoundEffect(AudioManager.FX_KEYPRESS_SPACEBAR);
                break;
            case Keyboard.KEYCODE_DONE:
            case 10:
                am.playSoundEffect(AudioManager.FX_KEYPRESS_RETURN);
                break;
            case Keyboard.KEYCODE_DELETE:
                am.playSoundEffect(AudioManager.FX_KEYPRESS_DELETE);
                break;
            default: am.playSoundEffect(AudioManager.FX_KEYPRESS_STANDARD);
        }
    }
    @Override
    public void onKey(int primaryCode, int[] keyCodes) {
        InputConnection ic = getCurrentInputConnection();
        playClick(primaryCode);
        switch(primaryCode){
            case Keyboard.KEYCODE_DELETE :
                ic.deleteSurroundingText(1, 0);
                break;
            case Keyboard.KEYCODE_SHIFT:
                caps = !caps;
                keyboard.setShifted(caps);
                kv.invalidateAllKeys();
                break;
            case Keyboard.KEYCODE_DONE:
                // Perform conversion and commitText
                String currentText = ic.getExtractedText(new ExtractedTextRequest(), 0).text.toString();

                // Apply preprocessing steps to the input text
                String preprocessedText = preprocessText(currentText);

                // Replace generateFormalConversion with your model inference code
                String convertedText = generateFormalConversion(preprocessedText);

                ic.commitText(convertedText, 1);
                ic.sendKeyEvent(new KeyEvent(KeyEvent.ACTION_DOWN, KeyEvent.KEYCODE_ENTER));
                break;
            default:
                char code = (char)primaryCode;
                if(Character.isLetter(code) && caps){
                    code = Character.toUpperCase(code);
                }
                ic.commitText(String.valueOf(code),1);
        }
    }

    private String preprocessText(String text) {
        // Implement your preprocessing steps here
        // For example: tokenization, lowercasing, removing punctuation, etc.
        // Return the preprocessed text
        // Create an empty annotation
        Annotation document = new Annotation(text);

        // Annotate the text using the Stanford NLP pipeline
        pipeline.annotate(document);

        // Process the annotated text and apply preprocessing logic
        List<CoreMap> sentences = document.get(CoreAnnotations.SentencesAnnotation.class);

        // Initialize a StringBuilder to store the preprocessed text
        StringBuilder preprocessedText = new StringBuilder();

        // Iterate through sentences and tokens to apply preprocessing
        for (CoreMap sentence : sentences) {
            List<CoreLabel> tokens = sentence.get(CoreAnnotations.TokensAnnotation.class);

            for (CoreLabel token : tokens) {
                String word = token.get(CoreAnnotations.TextAnnotation.class);

                // Convert the word to lowercase
                String lowercaseWord = word.toLowerCase();

                // Append the lowercase word to the StringBuilder
                preprocessedText.append(lowercaseWord).append(" ");
            }
        }

        // Get the preprocessed text as a String
        return preprocessedText.toString().trim();
    }

    private String generateFormalConversion(String preprocessedText) {
        // Replace this with your model inference code
        // Generate and return the formal conversion suggestion
        // You may need to use the loaded weka model for this purpose
        return preprocessedText; // Placeholder return value
    }
    private String convertToFormal(String informalText) {
        // Create an empty annotation
        Annotation document = new Annotation(informalText);

        // Annotate the text using the Stanford NLP pipeline
        pipeline.annotate(document);

        // Process the annotated text and apply conversion logic
        List<CoreMap> sentences = document.get(CoreAnnotations.SentencesAnnotation.class);

        // Initialize a StringBuilder to store the modified text
        StringBuilder modifiedText = new StringBuilder();

        // Iterate through sentences and apply conversion logic
        for (CoreMap sentence : sentences) {
            List<CoreLabel> tokens = sentence.get(CoreAnnotations.TokensAnnotation.class);

            for (CoreLabel token : tokens) {
                String word = token.get(CoreAnnotations.TextAnnotation.class);
                String posTag = token.get(CoreAnnotations.PartOfSpeechAnnotation.class);

                // Apply conversion logic here and modify the word if needed
                String modifiedWord = convertTokenToFormal(word, posTag);

                // Append the modified word to the StringBuilder
                modifiedText.append(modifiedWord).append(" ");
            }
        }

        // Get the modified text as a String
        return modifiedText.toString();
    }



    @Override
    public void onPress(int primaryCode) {
    }
    @Override
    public void onRelease(int primaryCode) {
    }
    @Override
    public void onText(CharSequence text) {
    }
    @Override
    public void swipeDown() {
    }
    @Override
    public void swipeLeft() {
    }
    @Override
    public void swipeRight() {
    }
    @Override
    public void swipeUp() {
    }
}

