var clover = new Object();

// JSON: {classes : [{name, id, sl, el,  methods : [{sl, el}, ...]}, ...]}
clover.pageData = {"classes":[{"el":133,"id":2037,"methods":[{"el":41,"sc":5,"sl":39},{"el":51,"sc":5,"sl":48},{"el":55,"sc":5,"sl":53},{"el":67,"sc":5,"sl":64},{"el":71,"sc":5,"sl":69},{"el":75,"sc":5,"sl":73},{"el":79,"sc":5,"sl":77},{"el":83,"sc":5,"sl":81},{"el":91,"sc":5,"sl":85},{"el":102,"sc":5,"sl":96},{"el":106,"sc":5,"sl":104},{"el":113,"sc":5,"sl":111},{"el":122,"sc":5,"sl":120},{"el":131,"sc":5,"sl":129}],"name":"TestCase","sl":34}]}

// JSON: {test_ID : {"methods": [ID1, ID2, ID3...], "name" : "testXXX() void"}, ...};
clover.testTargets = {"test_11":{"methods":[{"sl":48},{"sl":64},{"sl":104}],"name":"fixTableHeadsWithTagListReportOuput","pass":true,"statements":[{"sl":50},{"sl":66},{"sl":105}]},"test_12":{"methods":[{"sl":48},{"sl":64},{"sl":69}],"name":"shouldSplitOnStarts","pass":true,"statements":[{"sl":50},{"sl":66},{"sl":70}]},"test_14":{"methods":[{"sl":48},{"sl":64},{"sl":69}],"name":"shouldSplitJoinSeparatorAfter","pass":true,"statements":[{"sl":50},{"sl":66},{"sl":70}]},"test_18":{"methods":[{"sl":48},{"sl":64},{"sl":111}],"name":"shouldEnsureHeadingIds","pass":true,"statements":[{"sl":50},{"sl":66},{"sl":112}]},"test_20":{"methods":[{"sl":48},{"sl":64},{"sl":77},{"sl":81}],"name":"shouldExtract","pass":true,"statements":[{"sl":50},{"sl":66},{"sl":78},{"sl":82}]},"test_23":{"methods":[{"sl":48},{"sl":64},{"sl":69}],"name":"shouldNotSplit","pass":true,"statements":[{"sl":50},{"sl":66},{"sl":70}]},"test_24":{"methods":[{"sl":48},{"sl":64},{"sl":77}],"name":"shouldHeadingTree","pass":true,"statements":[{"sl":50},{"sl":66},{"sl":78}]},"test_27":{"methods":[{"sl":48},{"sl":64},{"sl":111}],"name":"shouldReorderToTopOneSection","pass":true,"statements":[{"sl":50},{"sl":66},{"sl":112}]},"test_33":{"methods":[{"sl":48},{"sl":64},{"sl":111}],"name":"shouldHeadingAnchorToId","pass":true,"statements":[{"sl":50},{"sl":66},{"sl":112}]},"test_35":{"methods":[{"sl":48},{"sl":64},{"sl":69}],"name":"shouldSplitJoinSeparatorNo","pass":true,"statements":[{"sl":50},{"sl":66},{"sl":70}]},"test_38":{"methods":[{"sl":48},{"sl":64},{"sl":69}],"name":"shouldSplitRecursively","pass":true,"statements":[{"sl":50},{"sl":66},{"sl":70}]},"test_43":{"methods":[{"sl":48},{"sl":64},{"sl":69}],"name":"shouldSplitJoinSeparatorBefore","pass":true,"statements":[{"sl":50},{"sl":66},{"sl":70}]},"test_44":{"methods":[{"sl":48},{"sl":64},{"sl":69}],"name":"shouldSplitBodyFragment","pass":true,"statements":[{"sl":50},{"sl":66},{"sl":70}]}}

// JSON: { lines : [{tests : [testid1, testid2, testid3, ...]}, ...]};
clover.srcFileLines = [[], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [38, 43, 27, 35, 11, 18, 44, 23, 24, 12, 14, 33, 20], [], [38, 43, 27, 35, 11, 18, 44, 23, 24, 12, 14, 33, 20], [], [], [], [], [], [], [], [], [], [], [], [], [], [38, 43, 27, 35, 11, 18, 44, 23, 24, 12, 14, 33, 20], [], [38, 43, 27, 35, 11, 18, 44, 23, 24, 12, 14, 33, 20], [], [], [38, 43, 35, 44, 23, 12, 14], [38, 43, 35, 44, 23, 12, 14], [], [], [], [], [], [], [24, 20], [24, 20], [], [], [20], [20], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [11], [11], [], [], [], [], [], [27, 18, 33], [27, 18, 33], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], []]