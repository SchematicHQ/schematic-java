# Credit lease conformance suite

`SPEC.md` and `vectors/*.json` are copied verbatim from `conformance/` in
[schematic-node](https://github.com/SchematicHQ/schematic-node), the reference
implementation. Do not edit them here: change them there, then copy the new
versions across, so every SDK runs the same contract.

The runner is the only language-specific piece. This SDK's lives in
`src/test/java/com/schematic/api/credits/conformance/`, and runs every vector
against both store backends: the in-memory stores and the Redis stores.
