package path;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by Belhaver on 11.02.2017.
 */
public class IdSubstitution {

    public static final int NORMAL_TYPE = 0;
    public static final int REVERT_TYPE = 1;


    private static Map<Integer, Integer> mIndexSubstituteMap = new HashMap<>();
    private static Map<Integer, Integer> mRevertIndexSubstituteMap = new HashMap<>();
    private static IdSubstitution ourInstance = new IdSubstitution();

    public static IdSubstitution getInstance() {
        return ourInstance;
    }

    private IdSubstitution() {
    }

    public int getIdSubstitute(Integer id, int type) {
        if (mIndexSubstituteMap == null) {
            mIndexSubstituteMap = new HashMap<>();
            mRevertIndexSubstituteMap = new HashMap<>();
        }

        if (type == NORMAL_TYPE) {
            if (mIndexSubstituteMap.containsKey(id)) {
                return mIndexSubstituteMap.get(id);
            } else {
                mIndexSubstituteMap.put(id, mIndexSubstituteMap.size());
                mRevertIndexSubstituteMap.put(mRevertIndexSubstituteMap.size(), id);
                return mIndexSubstituteMap.get(id);
            }
        } else {
            return mRevertIndexSubstituteMap.get(id);
        }
    }

    public Map<Integer, Integer> getIndexSubstituteMap() {
        return mIndexSubstituteMap;
    }

    public Map<Integer, Integer> getRevertIndexSubstituteMap() {
        return mRevertIndexSubstituteMap;
    }
}
