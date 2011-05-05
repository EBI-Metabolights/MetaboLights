var templates = [
  {"name":"benzene",
    "atoms":[
      {"symbol":"C","coord": {"x":2.5981,"y":0.75,"z":0},"charge":0},
      {"symbol":"C","coord": {"x":2.5981,"y":2.25,"z":0},"charge":0},
      {"symbol":"C","coord": {"x":1.30,"y":3.0,"z":0},"charge":0},
      {"symbol":"C","coord": {"x":0,"y":2.25,"z":0},"charge":0},
      {"symbol":"C","coord": {"x":0,"y":0.75,"z":0},"charge":0},
      {"symbol":"C","coord": {"x":1.30,"y":0,"z":0},"charge":0}],
    "bondindex":[
      {"source":0,"target":1,"type":"DOUBLE_BOND","stereo":"NOT_STEREO"},
      {"source":1,"target":2,"type":"SINGLE_BOND","stereo":"NOT_STEREO"},
      {"source":2,"target":3,"type":"DOUBLE_BOND","stereo":"NOT_STEREO"},
      {"source":3,"target":4,"type":"SINGLE_BOND","stereo":"NOT_STEREO"},
      {"source":4,"target":5,"type":"DOUBLE_BOND","stereo":"NOT_STEREO"},
      {"source":5,"target":0,"type":"SINGLE_BOND","stereo":"NOT_STEREO"}]
  },
  {"name":"cyclohexane",
   "atoms":[
     {"symbol":"C","coord": {"x":2.5981,"y":0.75,"z":0},"charge":0},
     {"symbol":"C","coord": {"x":2.5981,"y":2.25,"z":0},"charge":0},
     {"symbol":"C","coord": {"x":1.30,"y":3.0,"z":0},"charge":0},
     {"symbol":"C","coord": {"x":0,"y":2.25,"z":0},"charge":0},
     {"symbol":"C","coord": {"x":0,"y":0.75,"z":0},"charge":0},
     {"symbol":"C","coord": {"x":1.30,"y":0,"z":0},"charge":0}],
   "bondindex":[
     {"source":0,"target":1,"type":"SINGLE_BOND","stereo":"NOT_STEREO"},
     {"source":1,"target":2,"type":"SINGLE_BOND","stereo":"NOT_STEREO"},
     {"source":2,"target":3,"type":"SINGLE_BOND","stereo":"NOT_STEREO"},
     {"source":3,"target":4,"type":"SINGLE_BOND","stereo":"NOT_STEREO"},
     {"source":4,"target":5,"type":"SINGLE_BOND","stereo":"NOT_STEREO"},
     {"source":5,"target":0,"type":"SINGLE_BOND","stereo":"NOT_STEREO"}]
  },
  {"name":"cyclopentane",
   "atoms":[
    {"symbol":"C","coord": {"x":-2.3083,"y":0.4635,"z":0},"charge":0},
    {"symbol":"C","coord": {"x":-2.3083,"y":1.9635,"z":0},"charge":0},
    {"symbol":"C","coord": {"x":-0.8817,"y":2.427,"z":0},"charge":0},
    {"symbol":"C","coord": {"x":0,"y":1.2135,"z":0},"charge":0},
    {"symbol":"C","coord": {"x":-0.8817,"y":0,"z":0},"charge":0}],
  "bondindex":[
    {"source":0,"target":1,"type":"SINGLE_BOND","stereo":"NOT_STEREO"},
    {"source":1,"target":2,"type":"SINGLE_BOND","stereo":"NOT_STEREO"},
    {"source":2,"target":3,"type":"SINGLE_BOND","stereo":"NOT_STEREO"},
    {"source":3,"target":4,"type":"SINGLE_BOND","stereo":"NOT_STEREO"},
    {"source":4,"target":0,"type":"SINGLE_BOND","stereo":"NOT_STEREO"}]
  },
  {"name":"cyclopentane",
   "atoms":[
     {"symbol":"C","coord": {"x":2.5981,"y":0.75,"z":0},"charge":0},
     {"symbol":"C","coord": {"x":2.5981,"y":2.25,"z":0},"charge":0},
     {"symbol":"C","coord": {"x":0,"y":2.25,"z":0},"charge":0},
     {"symbol":"C","coord": {"x":0,"y":0.75,"z":0},"charge":0},
     {"symbol":"C","coord": {"x":1.30,"y":0,"z":0},"charge":0}],
   "bondindex":[
     {"source":0,"target":1,"type":"SINGLE_BOND","stereo":"NOT_STEREO"},
     {"source":1,"target":2,"type":"SINGLE_BOND","stereo":"NOT_STEREO"},
     {"source":2,"target":3,"type":"SINGLE_BOND","stereo":"NOT_STEREO"},
     {"source":3,"target":4,"type":"SINGLE_BOND","stereo":"NOT_STEREO"},
     {"source":4,"target":0,"type":"SINGLE_BOND","stereo":"NOT_STEREO"}]
  },
  {"name":"pyrrole",
   "atoms":[
     {"symbol":"C","coord": {"x":2.5981,"y":0.75,"z":0},"charge":0},
     {"symbol":"C","coord": {"x":2.5981,"y":2.25,"z":0},"charge":0},
     {"symbol":"C","coord": {"x":0,"y":2.25,"z":0},"charge":0},
     {"symbol":"C","coord": {"x":0,"y":0.75,"z":0},"charge":0},
     {"symbol":"N","coord": {"x":1.30,"y":0,"z":0},"charge":0}],
   "bondindex":[
     {"source":0,"target":1,"type":"DOUBLE_BOND","stereo":"NOT_STEREO"},
     {"source":1,"target":2,"type":"SINGLE_BOND","stereo":"NOT_STEREO"},
     {"source":2,"target":3,"type":"DOUBLE_BOND","stereo":"NOT_STEREO"},
     {"source":3,"target":4,"type":"SINGLE_BOND","stereo":"NOT_STEREO"},
     {"source":4,"target":0,"type":"SINGLE_BOND","stereo":"NOT_STEREO"}]
  },
    {"name":"pyrrole",
   "atoms":[
    {"symbol":"C","coord": {"x":-2.3083,"y":0.4635,"z":0},"charge":0},
    {"symbol":"C","coord": {"x":-2.3083,"y":1.9635,"z":0},"charge":0},
    {"symbol":"C","coord": {"x":-0.8817,"y":2.427,"z":0},"charge":0},
    {"symbol":"C","coord": {"x":0,"y":1.2135,"z":0},"charge":0},
    {"symbol":"N","coord": {"x":-0.8817,"y":0,"z":0},"charge":0}],
  "bondindex":[
    {"source":0,"target":1,"type":"DOUBLE_BOND","stereo":"NOT_STEREO"},
    {"source":1,"target":2,"type":"SINGLE_BOND","stereo":"NOT_STEREO"},
    {"source":2,"target":3,"type":"DOUBLE_BOND","stereo":"NOT_STEREO"},
    {"source":3,"target":4,"type":"SINGLE_BOND","stereo":"NOT_STEREO"},
    {"source":4,"target":0,"type":"SINGLE_BOND","stereo":"NOT_STEREO"}]
  },
  {"name":"naphthalene",
   "atoms":[
     {"symbol":"C","coord": {"x":2.5981,"y":0.75,"z":0},"charge":0},
     {"symbol":"C","coord": {"x":2.5981,"y":2.25,"z":0},"charge":0},
     {"symbol":"C","coord": {"x":1.2991,"y":3,"z":0},"charge":0},
     {"symbol":"C","coord": {"x":0,"y":2.25,"z":0},"charge":0},
     {"symbol":"C","coord": {"x":0,"y":0.75,"z":0},"charge":0},
     {"symbol":"C","coord": {"x":1.2991,"y":0,"z":0},"charge":0},
     {"symbol":"C","coord": {"x":3.8971,"y":3,"z":0},"charge":0},
     {"symbol":"C","coord": {"x":5.1962,"y":2.25,"z":0},"charge":0},
     {"symbol":"C","coord": {"x":5.1962,"y":0.75,"z":0},"charge":0},
     {"symbol":"C","coord": {"x":3.8971,"y":0,"z":0},"charge":0}],
   "bondindex":[
     {"source":0,"target":1,"type":"DOUBLE_BOND","stereo":"NOT_STEREO"},
     {"source":1,"target":2,"type":"SINGLE_BOND","stereo":"NOT_STEREO"},
     {"source":2,"target":3,"type":"DOUBLE_BOND","stereo":"NOT_STEREO"},
     {"source":3,"target":4,"type":"SINGLE_BOND","stereo":"NOT_STEREO"},
     {"source":4,"target":5,"type":"DOUBLE_BOND","stereo":"NOT_STEREO"},
     {"source":5,"target":0,"type":"SINGLE_BOND","stereo":"NOT_STEREO"},
     {"source":1,"target":6,"type":"SINGLE_BOND","stereo":"NOT_STEREO"},
     {"source":6,"target":7,"type":"DOUBLE_BOND","stereo":"NOT_STEREO"},
     {"source":7,"target":8,"type":"SINGLE_BOND","stereo":"NOT_STEREO"},
     {"source":8,"target":9,"type":"DOUBLE_BOND","stereo":"NOT_STEREO"},
     {"source":9,"target":0,"type":"SINGLE_BOND","stereo":"NOT_STEREO"}]
  }
];
