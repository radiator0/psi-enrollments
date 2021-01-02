import axios from 'axios';
import { ICrudGetAction, ICrudGetAllAction, ICrudPutAction, ICrudDeleteAction } from 'react-jhipster';

import { cleanEntity } from 'app/shared/util/entity-utils';
import { REQUEST, SUCCESS, FAILURE } from 'app/shared/reducers/action-type.util';

import { IClassUnit, defaultValue } from 'app/shared/model/class-unit.model';

export const ACTION_TYPES = {
  FETCH_CLASSUNIT_LIST: 'classUnit/FETCH_CLASSUNIT_LIST',
  FETCH_CLASSUNIT: 'classUnit/FETCH_CLASSUNIT',
  CREATE_CLASSUNIT: 'classUnit/CREATE_CLASSUNIT',
  UPDATE_CLASSUNIT: 'classUnit/UPDATE_CLASSUNIT',
  DELETE_CLASSUNIT: 'classUnit/DELETE_CLASSUNIT',
  RESET: 'classUnit/RESET'
};

const initialState = {
  loading: false,
  errorMessage: null,
  entities: [] as ReadonlyArray<IClassUnit>,
  entity: defaultValue,
  updating: false,
  updateSuccess: false
};

export type ClassUnitState = Readonly<typeof initialState>;

// Reducer

export default (state: ClassUnitState = initialState, action): ClassUnitState => {
  switch (action.type) {
    case REQUEST(ACTION_TYPES.FETCH_CLASSUNIT_LIST):
    case REQUEST(ACTION_TYPES.FETCH_CLASSUNIT):
      return {
        ...state,
        errorMessage: null,
        updateSuccess: false,
        loading: true
      };
    case REQUEST(ACTION_TYPES.CREATE_CLASSUNIT):
    case REQUEST(ACTION_TYPES.UPDATE_CLASSUNIT):
    case REQUEST(ACTION_TYPES.DELETE_CLASSUNIT):
      return {
        ...state,
        errorMessage: null,
        updateSuccess: false,
        updating: true
      };
    case FAILURE(ACTION_TYPES.FETCH_CLASSUNIT_LIST):
    case FAILURE(ACTION_TYPES.FETCH_CLASSUNIT):
    case FAILURE(ACTION_TYPES.CREATE_CLASSUNIT):
    case FAILURE(ACTION_TYPES.UPDATE_CLASSUNIT):
    case FAILURE(ACTION_TYPES.DELETE_CLASSUNIT):
      return {
        ...state,
        loading: false,
        updating: false,
        updateSuccess: false,
        errorMessage: action.payload
      };
    case SUCCESS(ACTION_TYPES.FETCH_CLASSUNIT_LIST):
      return {
        ...state,
        loading: false,
        entities: action.payload.data
      };
    case SUCCESS(ACTION_TYPES.FETCH_CLASSUNIT):
      return {
        ...state,
        loading: false,
        entity: action.payload.data
      };
    case SUCCESS(ACTION_TYPES.CREATE_CLASSUNIT):
    case SUCCESS(ACTION_TYPES.UPDATE_CLASSUNIT):
      return {
        ...state,
        updating: false,
        updateSuccess: true,
        entity: action.payload.data
      };
    case SUCCESS(ACTION_TYPES.DELETE_CLASSUNIT):
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

const apiUrl = 'api/class-units';

// Actions

export const getEntities: ICrudGetAllAction<IClassUnit> = (page, size, sort) => ({
  type: ACTION_TYPES.FETCH_CLASSUNIT_LIST,
  payload: axios.get<IClassUnit>(`${apiUrl}?cacheBuster=${new Date().getTime()}`)
});

export const getEntity: ICrudGetAction<IClassUnit> = id => {
  const requestUrl = `${apiUrl}/${id}`;
  return {
    type: ACTION_TYPES.FETCH_CLASSUNIT,
    payload: axios.get<IClassUnit>(requestUrl)
  };
};

export const createEntity: ICrudPutAction<IClassUnit> = entity => async dispatch => {
  const result = await dispatch({
    type: ACTION_TYPES.CREATE_CLASSUNIT,
    payload: axios.post(apiUrl, cleanEntity(entity))
  });
  dispatch(getEntities());
  return result;
};

export const updateEntity: ICrudPutAction<IClassUnit> = entity => async dispatch => {
  const result = await dispatch({
    type: ACTION_TYPES.UPDATE_CLASSUNIT,
    payload: axios.put(apiUrl, cleanEntity(entity))
  });
  dispatch(getEntities());
  return result;
};

export const deleteEntity: ICrudDeleteAction<IClassUnit> = id => async dispatch => {
  const requestUrl = `${apiUrl}/${id}`;
  const result = await dispatch({
    type: ACTION_TYPES.DELETE_CLASSUNIT,
    payload: axios.delete(requestUrl)
  });
  dispatch(getEntities());
  return result;
};

export const reset = () => ({
  type: ACTION_TYPES.RESET
});
