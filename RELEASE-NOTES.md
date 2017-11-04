* 1.1.2
   * Switch to jsr305 (javax.annotations) and use @ParametersAreNonnullByDefault
* 1.1.1
   * Fix issue with size being too big and causing iterator to go too far past the data.
* 1.1.0
   * Added the CharSequenceCollection for putting CharSequence lists in shared memory.
* 1.0.2
   * Fix package to agree with groupId (com.github.scr).
* 1.0.1
   * throw `NoSuchElementException` when accessing a primitive type (getFloat, e.g.) has no entry.
* 1.0.0
   * First version