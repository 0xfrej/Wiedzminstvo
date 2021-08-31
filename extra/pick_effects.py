import json
import random

# Pick random effects from list where it wasn't picked for this combination
# and where it wasn't picked in others more than once
# minimum of one beneficial effect
# minimum of one harmful effect

cache = []

def generateEffectTypes():
    result = []
    for i in range(0, 3):
        result[i] = random.randomInt()


with open("./effects.json", "r", encoding = "utf-8-sig") as effects_file:
    data = json.load(effects_file)

