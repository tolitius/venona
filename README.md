# venona

It all started from [Venona Project](http://en.wikipedia.org/wiki/Venona_project). And then the crypto tools just kept evolving... they still are.

## usage

Attacks are performed mostly "REPL style" using [venona.tools](https://github.com/tolitius/venona/blob/master/src/venona/tools.clj). 
For example a [venona.otp-attack](https://github.com/tolitius/venona/blob/master/src/venona/otp_attack.clj) is an attack on [One Time Pad](http://en.wikipedia.org/wiki/One-time_pad) where the same key was reused.

```clojure
;; a quick start into fascinating crypto universe:

user=> (use 'venona.otp-attack 'venona.tools)
nil
user=> (pprint (fmap xor-with-trigrams (trigram-at 0 xord-with-spaces)))
```

## license

Copyright © 2013 tolitius

Distributed under the Eclipse Public License, the same as Clojure.
