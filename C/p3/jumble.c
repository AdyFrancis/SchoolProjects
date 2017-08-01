/**
 * This program takes an a dictionary of words and
 * allows users to enter a series of letters that match
 * the words in the dictionary.
 * @Author Ady Francis
 */

#include <stdio.h>
#include <stdlib.h>
#include <stdbool.h>
#include <string.h>
#include <ctype.h>

/** Number of expected arguments */
#define ARG_NUM 2
/** Exit code for invalid exit */
#define INVALID_EXIT 1
/** maximum amount of words */
#define MAX_WORDS 100000
/** maximum length of words */
#define WORD_LENGTH 21
/** Command line argument array index for file name */
#define ARG_FILE 1

/** Array to hold all the words from input file */
char words[MAX_WORDS][WORD_LENGTH];

/** Number of words from the input file */
int wordCount;


/**
 * This function prompts the user for letters and stores them.
 * @param letters the array to store the user input to.
 */
bool getLetters (char * letters)
{
    printf("letters> ");

    while (scanf("%s", letters) != EOF) {
        if ((int)strlen(letters) > WORD_LENGTH) {
            printf("Invalid letters\n");
        }
        else {
            for (int i = 0; letters[i]; i++)
                if (isalpha(letters[i]) == 0 || islower(letters[i]) == 0) {
                    printf("Invalid letters\n");
                    break;
                }
        }
        return true;
    }
    return false;
}


/**
 * Checks to see if the word is within the length requirement,
 * if the words file contains more than 100,000 words,
 * and if the word contains invalid characters.
 * @param word the word string to be added to the words array
 * @return returns true if the word is valid
 */
bool checkWord(char word[])
{
    if ((int)strlen(word) > WORD_LENGTH - 1) {
        return false;
    }

    if (wordCount >= MAX_WORDS) {
        fprintf(stderr, "Invalid word file\n");
        exit(INVALID_EXIT);
    }

    for (int i = 0; word[i]; i++) {
        if (isalpha(word[i]) == 0) {
            return false;
        }
        else if (isupper(word[i]) != 0) {
            return false;
        }
    }
    return true;
}

/**
 * Reads the word list from the file with the given name
 * storing it in words and incrementing word count.
 * @param filename the name of the file with the word list.
 */
void readWords(char const *filename) {

    FILE *f = fopen(filename, "r");

    if (!f) {
        fprintf(stderr, "Can't open word file\n");
        exit(INVALID_EXIT);
    }

    char word[WORD_LENGTH];
    int i = 0;
    while (fscanf(f, "%s", word) == 1) {
        if (checkWord(word)) {
            for (int j = 0; word[j]; j++) {
                words[i][j] = word[j];
            }
            wordCount++;
            i++;
        }
        else {
            fprintf(stderr, "Invalid word file\n");
            exit(INVALID_EXIT);
        }
    }
}

/**
 * Sorts a string in alphabetical order
 * @param str the string that is to be sorted
 */
void sort(char *str)
{
    bool sorted = false;
    bool tried = false;
    int length = (int)strlen(str);
    while (!sorted) {
        for (int i = 0; i < length - 1; i++) {
            char c1 = str[i];
            char c2 = str[i + 1];
            if ((c1 - c2) > 0) {
                str[i] = c2;
                str[i + 1] = c1;
                tried = true;
            }
        }
        if (!tried)
            sorted = true;
        else tried = false;
    }
}

/**
 * Given a word and letters, this function returns true
 * if the given word contains the given letters.
 * @param word the word to be compared with the letters
 * @param letters the letters that the word parameter must contain
 * @return returns true if the given word contains only the given letters
 */
bool matches (char const *word, char const *letters)
{
    int wordLength = (int)strlen(word);
    int letLength = (int)strlen(letters);

    if (wordLength != letLength)
        return false;

    char str1[wordLength];
    char str2[letLength];
    strcpy(str1, word);
    strcpy(str2, letters);

    sort(str1);
    sort(str2);

    for (int i = 0; i < wordLength; i++)
        if (str1[i] != str2[i])
            return false;

    return true;
}

/**
 * Starting point of the program
 * @param argc the number of arguments provided in command line
 * @param argv array of command line arguments
 * @return exit status of the program
 */
int main(int argc, char *argv[])
{

    if (argc != ARG_NUM) {
        printf("Usage: jumble <word-file>\n");
        exit(INVALID_EXIT);
    }

    readWords(argv[ARG_FILE]);

    char letters[WORD_LENGTH];
    while (getLetters(letters)) {
        for (int i = 0; i < wordCount; i++)
            if (matches(words[i], letters))
                printf("%s\n", words[i]);
    }

    return EXIT_SUCCESS;
}
