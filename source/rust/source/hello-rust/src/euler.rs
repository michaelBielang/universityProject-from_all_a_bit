/// a typical euler problem to sum up all number to a given max where the value is dividable by 6 or by 9
/// but this is implemented like you could do in other programming languages
///
///
/// # Arguments
///
/// * `max` - Value until the euler problem algorithm should execute
///
/// # Examples
///
/// ```
/// let value = euler::euler_not_the_best(13);
/// assert_eq!(value, 27);
/// ```
pub fn euler_not_the_best(max: u64) -> u64 {
    let mut result: u64 = 0;
    let mut i: u64 = 0;
    loop {
        if i >= max {
            break;
        }
        if i % 6 == 0 || i % 9 == 0 {
            result += i;
        }
        i += 1;
    }
    return result;
}

/// a typical euler problem to sum up all number to a given max where the value is dividable by 6 or by 9
/// implemented in rust style
///
///
/// # Arguments
///
/// * `max` - Value until the euler problem algorithm should execute
///
/// # Examples
///
/// ```
/// let value = euler::euler_way_to_go(13);
/// assert_eq!(value, 27);
/// ```
pub fn euler_way_to_go(max: u64) -> u64 {
    (0..max).filter(|n| n % 6 == 0 || n % 9 == 0).sum()
}

#[cfg(test)] // Only compiles when running tests
mod tests {
    use super::*;

    #[test]
    fn test_normal_euler() {
        let value = euler_not_the_best(13);
        assert_eq!(value, 27);
    }

    #[test]
    fn test_rust_euler() {
        let value = euler_way_to_go(13);
        assert_eq!(value, 27);
    }
}
