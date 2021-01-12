import axios from 'axios';
import { ICrudGetAction, ICrudGetAllAction, ICrudPutAction, ICrudDeleteAction } from 'react-jhipster';

import { cleanEntity } from 'app/shared/util/entity-utils';
import { REQUEST, SUCCESS, FAILURE } from 'app/shared/reducers/action-type.util';

import { IEnrollmentUnit, defaultValue } from 'app/shared/model/enrollment-unit.model';

export const ACTION_TYPES = {
  FETCH_ENROLLMENTUNIT_LIST: 'enrollmentUnit/FETCH_ENROLLMENTUNIT_LIST',
  FETCH_ENROLLMENTUNIT: 'enrollmentUnit/FETCH_ENROLLMENTUNIT',
  CREATE_ENROLLMENTUNIT: 'enrollmentUnit/CREATE_ENROLLMENTUNIT',
  UPDATE_ENROLLMENTUNIT: 'enrollmentUnit/UPDATE_ENROLLMENTUNIT',
  DELETE_ENROLLMENTUNIT: 'enrollmentUnit/DELETE_ENROLLMENTUNIT',
  RESET: 'enrollmentUnit/RESET',
};

const initialState = {
  loading: false,
  errorMessage: null,
  entities: [] as ReadonlyArray<IEnrollmentUnit>,
  entity: defaultValue,
  updating: false,
  updateSuccess: false,
};

export type EnrollmentUnitState = Readonly<typeof initialState>;

// Reducer

export default (state: EnrollmentUnitState = initialState, action): EnrollmentUnitState => {
  switch (action.type) {
    case REQUEST(ACTION_TYPES.FETCH_ENROLLMENTUNIT_LIST):
    case REQUEST(ACTION_TYPES.FETCH_ENROLLMENTUNIT):
      return {
        ...state,
        errorMessage: null,
        updateSuccess: false,
        loading: true,
      };
    case REQUEST(ACTION_TYPES.CREATE_ENROLLMENTUNIT):
    case REQUEST(ACTION_TYPES.UPDATE_ENROLLMENTUNIT):
    case REQUEST(ACTION_TYPES.DELETE_ENROLLMENTUNIT):
      return {
        ...state,
        errorMessage: null,
        updateSuccess: false,
        updating: true,
      };
    case FAILURE(ACTION_TYPES.FETCH_ENROLLMENTUNIT_LIST):
    case FAILURE(ACTION_TYPES.FETCH_ENROLLMENTUNIT):
    case FAILURE(ACTION_TYPES.CREATE_ENROLLMENTUNIT):
    case FAILURE(ACTION_TYPES.UPDATE_ENROLLMENTUNIT):
    case FAILURE(ACTION_TYPES.DELETE_ENROLLMENTUNIT):
      return {
        ...state,
        loading: false,
        updating: false,
        updateSuccess: false,
        errorMessage: action.payload,
      };
    case SUCCESS(ACTION_TYPES.FETCH_ENROLLMENTUNIT_LIST):
      return {
        ...state,
        loading: false,
        entities: action.payload.data,
      };
    case SUCCESS(ACTION_TYPES.FETCH_ENROLLMENTUNIT):
      return {
        ...state,
        loading: false,
        entity: action.payload.data,
      };
    case SUCCESS(ACTION_TYPES.CREATE_ENROLLMENTUNIT):
    case SUCCESS(ACTION_TYPES.UPDATE_ENROLLMENTUNIT):
      return {
        ...state,
        updating: false,
        updateSuccess: true,
        entity: action.payload.data,
      };
    case SUCCESS(ACTION_TYPES.DELETE_ENROLLMENTUNIT):
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

const apiUrl = 'api/enrollment-units';

// Actions

export const getEntities: ICrudGetAllAction<IEnrollmentUnit> = (page, size, sort) => ({
  type: ACTION_TYPES.FETCH_ENROLLMENTUNIT_LIST,
  payload: axios.get<IEnrollmentUnit>(`${apiUrl}?cacheBuster=${new Date().getTime()}`),
});

export const getEntity: ICrudGetAction<IEnrollmentUnit> = id => {
  const requestUrl = `${apiUrl}/${id}`;
  return {
    type: ACTION_TYPES.FETCH_ENROLLMENTUNIT,
    payload: axios.get<IEnrollmentUnit>(requestUrl),
  };
};

export const createEntity: ICrudPutAction<IEnrollmentUnit> = entity => async dispatch => {
  const result = await dispatch({
    type: ACTION_TYPES.CREATE_ENROLLMENTUNIT,
    payload: axios.post(apiUrl, cleanEntity(entity)),
  });
  dispatch(getEntities());
  return result;
};

export const updateEntity: ICrudPutAction<IEnrollmentUnit> = entity => async dispatch => {
  const result = await dispatch({
    type: ACTION_TYPES.UPDATE_ENROLLMENTUNIT,
    payload: axios.put(apiUrl, cleanEntity(entity)),
  });
  return result;
};

export const deleteEntity: ICrudDeleteAction<IEnrollmentUnit> = id => async dispatch => {
  const requestUrl = `${apiUrl}/${id}`;
  const result = await dispatch({
    type: ACTION_TYPES.DELETE_ENROLLMENTUNIT,
    payload: axios.delete(requestUrl),
  });
  dispatch(getEntities());
  return result;
};

export const reset = () => ({
  type: ACTION_TYPES.RESET,
});
