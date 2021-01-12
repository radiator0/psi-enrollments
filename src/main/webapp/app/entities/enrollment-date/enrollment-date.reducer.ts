import axios from 'axios';
import { ICrudGetAction, ICrudGetAllAction, ICrudPutAction, ICrudDeleteAction } from 'react-jhipster';

import { cleanEntity } from 'app/shared/util/entity-utils';
import { REQUEST, SUCCESS, FAILURE } from 'app/shared/reducers/action-type.util';

import { IEnrollmentDate, defaultValue } from 'app/shared/model/enrollment-date.model';

export const ACTION_TYPES = {
  FETCH_ENROLLMENTDATE_LIST: 'enrollmentDate/FETCH_ENROLLMENTDATE_LIST',
  FETCH_ENROLLMENTDATE: 'enrollmentDate/FETCH_ENROLLMENTDATE',
  CREATE_ENROLLMENTDATE: 'enrollmentDate/CREATE_ENROLLMENTDATE',
  UPDATE_ENROLLMENTDATE: 'enrollmentDate/UPDATE_ENROLLMENTDATE',
  DELETE_ENROLLMENTDATE: 'enrollmentDate/DELETE_ENROLLMENTDATE',
  RESET: 'enrollmentDate/RESET',
};

const initialState = {
  loading: false,
  errorMessage: null,
  entities: [] as ReadonlyArray<IEnrollmentDate>,
  entity: defaultValue,
  updating: false,
  updateSuccess: false,
};

export type EnrollmentDateState = Readonly<typeof initialState>;

// Reducer

export default (state: EnrollmentDateState = initialState, action): EnrollmentDateState => {
  switch (action.type) {
    case REQUEST(ACTION_TYPES.FETCH_ENROLLMENTDATE_LIST):
    case REQUEST(ACTION_TYPES.FETCH_ENROLLMENTDATE):
      return {
        ...state,
        errorMessage: null,
        updateSuccess: false,
        loading: true,
      };
    case REQUEST(ACTION_TYPES.CREATE_ENROLLMENTDATE):
    case REQUEST(ACTION_TYPES.UPDATE_ENROLLMENTDATE):
    case REQUEST(ACTION_TYPES.DELETE_ENROLLMENTDATE):
      return {
        ...state,
        errorMessage: null,
        updateSuccess: false,
        updating: true,
      };
    case FAILURE(ACTION_TYPES.FETCH_ENROLLMENTDATE_LIST):
    case FAILURE(ACTION_TYPES.FETCH_ENROLLMENTDATE):
    case FAILURE(ACTION_TYPES.CREATE_ENROLLMENTDATE):
    case FAILURE(ACTION_TYPES.UPDATE_ENROLLMENTDATE):
    case FAILURE(ACTION_TYPES.DELETE_ENROLLMENTDATE):
      return {
        ...state,
        loading: false,
        updating: false,
        updateSuccess: false,
        errorMessage: action.payload,
      };
    case SUCCESS(ACTION_TYPES.FETCH_ENROLLMENTDATE_LIST):
      return {
        ...state,
        loading: false,
        entities: action.payload.data,
      };
    case SUCCESS(ACTION_TYPES.FETCH_ENROLLMENTDATE):
      return {
        ...state,
        loading: false,
        entity: action.payload.data,
      };
    case SUCCESS(ACTION_TYPES.CREATE_ENROLLMENTDATE):
    case SUCCESS(ACTION_TYPES.UPDATE_ENROLLMENTDATE):
      return {
        ...state,
        updating: false,
        updateSuccess: true,
        entity: action.payload.data,
      };
    case SUCCESS(ACTION_TYPES.DELETE_ENROLLMENTDATE):
      return {
        ...state,
        updating: false,
        updateSuccess: true,
        entity: {},
      };
    case ACTION_TYPES.RESET:
      return {
        ...initialState,
      };
    default:
      return state;
  }
};

const apiUrl = 'api/enrollment-dates';

// Actions

export const getEntities: ICrudGetAllAction<IEnrollmentDate> = (page, size, sort) => ({
  type: ACTION_TYPES.FETCH_ENROLLMENTDATE_LIST,
  payload: axios.get<IEnrollmentDate>(`${apiUrl}?cacheBuster=${new Date().getTime()}`),
});

export const getEntity: ICrudGetAction<IEnrollmentDate> = id => {
  const requestUrl = `${apiUrl}/${id}`;
  return {
    type: ACTION_TYPES.FETCH_ENROLLMENTDATE,
    payload: axios.get<IEnrollmentDate>(requestUrl),
  };
};

export const createEntity: ICrudPutAction<IEnrollmentDate> = entity => async dispatch => {
  const result = await dispatch({
    type: ACTION_TYPES.CREATE_ENROLLMENTDATE,
    payload: axios.post(apiUrl, cleanEntity(entity)),
  });
  dispatch(getEntities());
  return result;
};

export const updateEntity: ICrudPutAction<IEnrollmentDate> = entity => async dispatch => {
  const result = await dispatch({
    type: ACTION_TYPES.UPDATE_ENROLLMENTDATE,
    payload: axios.put(apiUrl, cleanEntity(entity)),
  });
  return result;
};

export const deleteEntity: ICrudDeleteAction<IEnrollmentDate> = id => async dispatch => {
  const requestUrl = `${apiUrl}/${id}`;
  const result = await dispatch({
    type: ACTION_TYPES.DELETE_ENROLLMENTDATE,
    payload: axios.delete(requestUrl),
  });
  dispatch(getEntities());
  return result;
};

export const reset = () => ({
  type: ACTION_TYPES.RESET,
});
