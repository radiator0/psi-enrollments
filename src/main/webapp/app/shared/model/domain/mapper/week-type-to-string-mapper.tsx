import { WeekType } from '../../enumerations/week-type.model';
import { translate } from 'react-jhipster';

const weekTypeToLongString = (wt: WeekType) => {
    return translate(`enrollmentsApp.WeekType.${wt}`);
}

const weekTypeToShortString = (wt: WeekType) => {
    return translate(`enrollmentsApp.ShortWeekType.${wt}`);
}

export { weekTypeToLongString, weekTypeToShortString }