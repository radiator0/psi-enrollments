import { SemesterPeriod } from '../../enumerations/semester-period.model';
import { translate } from 'react-jhipster';

const semesterPeriodToLongString = (sp: SemesterPeriod) => {
    return translate(`enrollmentsApp.SemesterPeriod.${sp}`);
}

const semesterPeriodToShortString = (sp: SemesterPeriod) => {
    return translate(`enrollmentsApp.ShortSemesterPeriod.${sp}`);
}

export { semesterPeriodToLongString, semesterPeriodToShortString }