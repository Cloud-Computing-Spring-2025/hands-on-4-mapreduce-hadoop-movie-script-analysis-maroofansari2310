# Analyze the movie script using Hadoop MapReduce

The **Movie Script Analysis** project implements a Hadoop MapReduce program to analyze a movie script dataset. The program processes a movie script where each line represents a character's dialogue.

# Approach and Implementation
The key tasks are:
1. **Most Frequent Words by Character**
   - Mapper (CharacterWordMapper): Extracts words from a character’s dialogue, assigning each a count of 1.
   - Reducer (CharacterWordReducer): Sums up occurrences of each word for a character and outputs the total frequency.
2. **Dialogue Length Analysis**
   - Mapper (DialogueLengthMapper): Outputs the character’s name along with the length of their dialogue.
   - Reducer (DialogueLengthReducer): Computes and returns the total dialogue length spoken by each character.
3. **Unique Words by Character**
   - Mapper (UniqueWordsMapper): Identifies and emits unique words from each character’s dialogue.
   - Reducer (UniqueWordsReducer): Collects and outputs the distinct words used by each character.

## Setup and Execution

### 1. **Start the Hadoop Cluster**

Run the following command to start the Hadoop cluster:

```bash
docker compose up -d
```

### 2. **Build the Code**

Build the code using Maven:

```bash
mvn install
```

### 3. **Move JAR File to Shared Folder**

Move the generated JAR file to a shared folder for easy access:

```bash
mv target/*.jar exec_jar/
```

### 4. **Copy JAR to Docker Container**

Copy the JAR file to the Hadoop ResourceManager container:

```bash
docker cp /workspaces/hands-on-4-mapreduce-hadoop-movie-script-analysis-maroofansari2310/exec_jar/hands-on2-movie-script-analysis-1.0-SNAPSHOT.jar resourcemanager:/opt/hadoop-3.2.1/share/hadoop/mapreduce/
```

### 5. **Move Dataset to Docker Container**

Copy the dataset to the Hadoop ResourceManager container:

```bash
docker cp input/movie_dialogues.txt resourcemanager:/opt/hadoop-3.2.1/share/hadoop/mapreduce/
```

### 6. **Connect to Docker Container**

Access the Hadoop ResourceManager container:

```bash
docker exec -it resourcemanager /bin/bash
```

Navigate to the Hadoop directory:

```bash
cd /opt/hadoop-3.2.1/share/hadoop/mapreduce/
```

### 7. **Set Up HDFS**

Create a folder in HDFS for the input dataset:

```bash
hadoop fs -mkdir -p /input/dataset
```

Copy the input dataset to the HDFS folder:

```bash
hadoop fs -put ./movie_dialogues.txt /input/dataset
```

### 8. **Execute the MapReduce Job**

Run your MapReduce job using the following command:

```bash
hadoop jar hands-on2-movie-script-analysis-1.0-SNAPSHOT.jar com.movie.script.analysis /input/dataset/movie_dialogues.txt /output
```

### 9. **View the Output**

To view the output of your MapReduce job, use:

```bash
hadoop fs -cat /output/*
```

### 10. **Copy Output from HDFS to Local OS**

To copy the output from HDFS to your local machine:

1. Use the following command to copy from HDFS:
    ```bash
    hdfs dfs -get /output /opt/hadoop-3.2.1/share/hadoop/mapreduce/
    ```

2. use Docker to copy from the container to your local machine:
   ```bash
   exit 
   ```
    ```bash
    docker cp resourcemanager:/opt/hadoop-3.2.1/share/hadoop/mapreduce/output/ output/
    ```
# Challenge faced
  - Jar File Path Mismatch: Fixed by verifying the exact location where the jar file was generated.

# Sample input:
   ```
   Sirius: I did my waiting! Twelve years of it! In Azkaban!
   Ron: That's brilliant!
   Voldemort: We could all have been killed - or worse, expelled.
   ```
# Sample output:
# Task 1:
```
- 1
I 1
did 1
my 1
waiting! 1
Twelve 1
years 1
of 1
it! 1
In 1
Azkaban! 1
That's 1
brilliant! 1
We 1
could 1
all 1
have 1
been 1
killed 1
or 1
worse 1
expelled 1
```
# Task 2:
```
Sirius 49
Ron 16
Voldemort 51
```
# Task 3:
```
Sirius [I, did, my, waiting!, Twelve, years, of, it!, In, Azkaban!]
Ron [That's, brilliant!]
Voldemort [We, could, all, have, been, killed, or, -, worse, expelled]
```
