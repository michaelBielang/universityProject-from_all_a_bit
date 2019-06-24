extern crate rand;

/// A typical selection sort programmed in rust style
///
/// # Arguments
///
/// * `list` - list to sort
///
/// # Examples
///
/// ```
/// let mut random_array: [u64; 8] = rand::random();
/// assert_eq!(random_array.sort(), sorting::selection_sort(&mut random_array));
/// ```
pub fn selection_sort<T: Ord>(list: &mut [T]) {
    for i in 0..list.len() {
        if let Some((j, _)) = list.iter().enumerate().skip(i).min_by_key(|x| x.1) {
            list.swap(i, j);
        }
    }
}

/// A typical insertion sort programmed in rust style
///
/// # Arguments
///
/// * `list` - list to sort
///
/// # Examples
///
/// ```
/// let mut random_array: [u64; 8] = rand::random();
/// assert_eq!(random_array.sort(), sorting::insertions_sort(&mut random_array));
/// ```
pub fn insertion_sort<T: Ord>(list: &mut [T]) {
    for i in 1..list.len() {
        for j in (1..i + 1).rev() {
            if list[j - 1] <= list[j] {
                break;
            }
            list.swap(j - 1, j)
        }
    }
}

#[cfg(test)]
mod tests {
    use super::*;
    use rand;

    #[test]
    fn test_selection_sort() {
        let mut random_array: [u64; 8] = rand::random();
        assert_eq!(random_array.sort(), selection_sort(&mut random_array));
    }

    #[test]
    fn test_insertion_sort() {
        let mut random_array: [u64; 8] = rand::random();
        assert_eq!(random_array.sort(), insertion_sort(&mut random_array));
    }
}
