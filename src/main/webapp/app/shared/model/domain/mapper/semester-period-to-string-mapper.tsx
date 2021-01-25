// @ts-nocheck
import SemesterPeriod from "../enum/semester-period"

const semesterPeriodToLongString = (sp: SemesterPeriod) => {
    switch (sp) {
        case 'Whole':
            return '';
        case 'FirstHalf':
            return 'First Half';
        case 'SecondHalf':
            return 'Second Half';
        default:
            return '';
    }
}

const semesterPeriodToShortString = (sp: SemesterPeriod) => {
    switch (sp) {
        case 'Whole':
            return '';
        case 'FirstHalf':
            return 'FH';
        case 'SecondHalf':
            return 'SH';
        default:
            return '';
    }
}

export { semesterPeriodToLongString, semesterPeriodToShortString }