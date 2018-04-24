package com.guidewire.wordy.impl;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;

@RunWith(Suite.class)
@SuiteClasses({ BoardImplTest.class, WordInBoardValidatorImplTest.class,
		WordScorerImplTest.class, WordValidatorImplTest.class,
		WordyImplTest.class })
public class AllTests {

}
