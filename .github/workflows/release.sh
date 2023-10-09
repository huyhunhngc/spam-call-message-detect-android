#!/bin/bash

mkdir -p distribution/whatsnew
output_file="./distribution/whatsnew/release_notes.txt" > "$output_file"  # Clear the file if it exists

parse_release_text() {
    local text="$(echo "$1" | sed -E 's/ by @[^ ]*//g; s| in https://[^ ]*||g')"
    local max_length="$2"
    echo "${text:0:max_length}"
}

release_notes=$(parse_release_text "$(gh release view --json body -q ".body")" 500)

echo "$release_notes" >> "$output_file"
echo >> "$output_file"
echo "Release notes (limited to 500 characters) have been saved to $output_file"