import axios from 'axios';
import { ICrudGetAction, ICrudGetAllAction, ICrudPutAction, ICrudDeleteAction } from 'react-jhipster';

import { cleanEntity } from 'app/shared/util/entity-utils';
import { REQUEST, SUCCESS, FAILURE } from 'app/shared/reducers/action-type.util';

import { IEnrollmentRight, defaultValue } from 'app/shared/model/enrollment-right.model';

export const ACTION_TYPES = {
  FETCH_ENROLLMENTRIGHT_LIST: 'enrollmentRight/FETCH_ENROLLMENTRIGHT_LIST',
  FETCH_ENROLLMENTRIGHT: 'enrollmentRight/FETCH_ENROLLMENTRIGHT',
  CREATE_ENROLLMENTRIGHT: 'enrollmentRight/CREATE_ENROLLMENTRIGHT',
  UPDATE_ENROLLMENTRIGHT: 'enrollmentRight/UPDATE_ENROLLMENTRIGHT',
  DELETE_ENROLLMENTRIGHT: 'enrollmentRight/DELETE_ENROLLMENTRIGHT',
  RESET: 'enrollmentRight/RESET'
};

const initialState = {
  loading: false,
  errorMessage: null,
  entities: [] as ReadonlyArray<IEnrollmentRight>,
  entity: defaultValue,
  updating: false,
  updateSuccess: false
};

export type EnrollmentRightState = Readonly<typeof initialState>;

// Reducer

export default (state: EnrollmentRightState = initialState, action): EnrollmentRightState => {
  switch (action.type) {
    case REQUEST(ACTION_TYPES.FETCH_ENROLLMENTRIGHT_LIST):
    case REQUEST(ACTION_TYPES.FETCH_ENROLLMENTRIGHT):
      return {
        ...state,
        errorMessage: null,
        updateSuccess: false,
        loading: true
      };
    case REQUEST(ACTION_TYPES.CREATE_ENROLLMENTRIGHT):
    case REQUEST(ACTION_TYPES.UPDATE_ENROLLMENTRIGHT):
    case REQUEST(ACTION_TYPES.DELETE_ENROLLMENTRIGHT):
      return {
        ...state,
        errorMessage: null,
        updateSuccess: false,
        updating: true
      };
    case FAILURE(ACTION_TYPES.FETCH_ENROLLMENTRIGHT_LIST):
    case FAILURE(ACTION_TYPES.FETCH_ENROLLMENTRIGHT):
    case FAILURE(ACTION_TYPES.CREATE_ENROLLMENTRIGHT):
    case FAILURE(ACTION_TYPES.UPDATE_ENROLLMENTRIGHT):
    case FAILURE(ACTION_TYPES.DELETE_ENROLLMENTRIGHT):
      return {
        ...state,
        loading: false,
        updating: false,
        updateSuccess: false,
        errorMessage: action.payload
      };
    case SUCCESS(ACTION_TYPES.FETCH_ENROLLMENTRIGHT_LIST):
      return {
        ...state,
        loading: false,
        entities: action.payload.data
      };
    case SUCCESS(ACTION_TYPES.FETCH_ENROLLMENTRIGHT):
      return {
        ...state,
        loading: false,
        entity: action.payload.data
      };
    case SUCCESS(ACTION_TYPES.CREATE_ENROLLMENTRIGHT):
    case SUCCESS(ACTION_TYPES.UPDATE_ENROLLMENTRIGHT):
      return {
        ...state,
        updating: false,
        updateSuccess: true,
        entity: action.payload.data
      };
    case SUCCESS(ACTION_TYPES.DELETE_ENROLLMENTRIGHT):
      return {
        ...state,
        updating: false,
        updateSuccess: true,
        entity: {}
      };
    case ACTION_TYPES.RESET:
      return {
        ...initialState
      };
    default:
      return state;
  }
};

const apiUrl = 'api/enrollment-rights';

// Actions

export const getEntities: ICrudGetAllAction<IEnrollmentRight> = (page, size, sort) => ({
  type: ACTION_TYPES.FETCH_ENROLLMENTRIGHT_LIST,
  payload: axios.get<IEnrollmentRight>(`${apiUrl}?cacheBuster=${new Date().getTime()}`)
});

export const getEntity: ICrudGetAction<IEnrollmentRight> = id => {
  const requestUrl = `${apiUrl}/${id}`;
  return {
    type: ACTION_TYPES.FETCH_ENROLLMENTRIGHT,
    payload: axios.get<IEnrollmentRight>(requestUrl)
  };
};

export const createEntity: ICrudPutAction<IEnrollmentRight> = entity => async dispatch => {
  const result = await dispatch({
    type: ACTION_TYPES.CREATE_ENROLLMENTRIGHT,
    payload: axios.post(apiUrl, cleanEntity(entity))
  });
  dispatch(getEntities());
  return result;
};

export const updateEntity: ICrudPutAction<IEnrollmentRight> = entity => async dispatch => {
  const result = await dispatch({
    type: ACTION_TYPES.UPDATE_ENROLLMENTRIGHT,
    payload: axios.put(apiUrl, cleanEntity(entity))
  });
  dispatch(getEntities());
  return result;
};

export const deleteEntity: ICrudDeleteAction<IEnrollmentRight> = id => async dispatch => {
  const requestUrl = `${apiUrl}/${id}`;
  const result = await dispatch({
    type: ACTION_TYPES.DELETE_ENROLLMENTRIGHT,
    payload: axios.delete(requestUrl)
  });
  dispatch(getEntities());
  return result;
};

export const reset = () => ({
  type: ACTION_TYPES.RESET
});
