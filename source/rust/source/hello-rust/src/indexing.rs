use std::collections::HashMap;
use std::sync::mpsc::channel;
use threadpool::ThreadPool;

/// function to create an reverse index with parallel working indexer
///
/// # Arguments
///
/// * `input` - array of strings that should be indexed
/// * `worker_count` - number of workers in the thread pool
///
/// # Examples
///
/// ```
///  let input = [("doc1", "This is an rust example. It should create an reverse index"),("doc2", "test example")]
///        .iter()
///        .cloned()
///        .collect();
/// let value = indexing::frequency(text, 2);
/// ```
pub fn frequency(
    input: &HashMap<&'static str, &'static str>,
    worker_count: usize,
) -> HashMap<String, HashMap<String, u32>> {
    let pool = ThreadPool::new(worker_count);
    let (tx, rx) = channel();

    for (&name, &text) in input {
        let thread_tx = tx.to_owned();
        pool.execute(move || thread_tx.send(word_count(&text)).ok().unwrap());
    }

    let mut result = HashMap::new();
    for (&name, text) in input {
        let thread_result = rx.recv().ok().unwrap();
        for (word, count) in thread_result {
            result
                .entry(word)
                .or_insert(HashMap::new())
                .insert(name.to_string(), count);
        }
    }
    result
}

fn word_count(words: &str) -> HashMap<String, u32> {
    let mut counts: HashMap<String, u32> = HashMap::new();
    for word in words
        .split(|c: char| c.is_whitespace() || c == ',' || c == ';' || c == '.')
        .map(|s| s.trim_matches(|c: char| !c.is_alphanumeric()))
        .filter(|s| !s.is_empty())
        .map(|s| s.to_lowercase().to_string())
    {
        counts.entry(word).and_modify(|v| *v += 1).or_insert(1);
    }
    counts
}

#[cfg(test)]
mod test {
    use super::*;

    #[test]
    fn test_no_texts() {
        assert_eq!(frequency(&HashMap::new(), 4), HashMap::new());
    }

    #[test]
    fn test_one_word_indexing() {
        let input = [("doc1", "test")].iter().cloned().collect();
        let value = frequency(&input, 4);

        assert!(value.contains_key("test"));
        assert_eq!(value["test"]["doc1"], 1);
    }

    #[test]
    fn test_one_word_twice_indexing() {
        let input = [("doc1", "test test")].iter().cloned().collect();
        let value = frequency(&input, 4);

        assert!(value.contains_key("test"));
        assert_eq!(value["test"]["doc1"], 2);
    }

    #[test]
    fn test_two_doc_one_word_indexing() {
        let input = [("doc1", "test"), ("doc2", "test")]
            .iter()
            .cloned()
            .collect();
        let value = frequency(&input, 2);

        assert!(value.contains_key("test"));
        let test = &value["test"];
        assert_eq!(test["doc1"], 1);
        assert_eq!(test["doc2"], 1);
    }

    #[test]
    fn test_one_doc_indexing() {
        let input = [(
            "doc1",
            "This is an rust example. It should create an reverse index",
        ), ("doc2", "This rust example should create an reverse index")]
        .iter()
        .cloned()
        .collect();
        let value = frequency(&input, 2);

        assert!(value.contains_key("this"));
        assert!(value.contains_key("example"));
        assert!(value.contains_key("an"));
        assert!(value.contains_key("is"));
        assert!(value.contains_key("rust"));
        assert!(value.contains_key("it"));
        assert!(value.contains_key("should"));
        assert!(value.contains_key("create"));
        assert!(value.contains_key("reverse"));
        assert!(value.contains_key("index"));

        assert_eq!(value["this"]["doc1"], 1);
        assert_eq!(value["example"]["doc1"], 1);
        assert_eq!(value["an"]["doc1"], 2);
        assert_eq!(value["is"]["doc1"], 1);
        assert_eq!(value["rust"]["doc1"], 1);
        assert_eq!(value["it"]["doc1"], 1);
        assert_eq!(value["should"]["doc1"], 1);
        assert_eq!(value["create"]["doc1"], 1);
        assert_eq!(value["reverse"]["doc1"], 1);
        assert_eq!(value["index"]["doc1"], 1);

        assert_eq!(value["this"]["doc2"], 1);
        assert_eq!(value["rust"]["doc2"], 1);
        assert_eq!(value["example"]["doc2"], 1);
        assert_eq!(value["should"]["doc2"], 1);
        assert_eq!(value["create"]["doc2"], 1);
        assert_eq!(value["an"]["doc2"], 1);
        assert_eq!(value["reverse"]["doc2"], 1);
        assert_eq!(value["index"]["doc2"], 1);
    }
}
