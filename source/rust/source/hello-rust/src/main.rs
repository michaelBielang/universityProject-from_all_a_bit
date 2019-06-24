//! This is an project for the DVA Praktikum at university of applied science augsburg.
//! Here we want to get a little insight into programming with Rust
fn main() {
    println!("Hello, world!");
}

/// A simple Module to show the how optimized you can write rust code
pub mod euler;

/// Module for some sorting algorithms
/// as benchmarks are not supported in current stable version we weren't able to to it
pub mod sorting;

/// Module to create an reverse index as an first step to an search engine
pub mod indexing;

