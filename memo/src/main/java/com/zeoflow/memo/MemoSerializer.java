package com.zeoflow.memo;

import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

class MemoSerializer implements ISerializer
{

    private static final char DELIMITER = '@';
    private static final String INFO_DELIMITER = "#";
    private static final char NEW_VERSION = 'V';

    private final ILogInterceptor logInterceptor;

    MemoSerializer(ILogInterceptor logInterceptor)
    {
        this.logInterceptor = logInterceptor;
    }

    @Override
    public <T> String serialize(String cipherText, T originalGivenValue)
    {
        MemoUtils.checkNullOrEmpty("Cipher text", cipherText);
        MemoUtils.checkNull("Value", originalGivenValue);

        String keyClassName = "";
        String valueClassName = "";
        char dataType;
        if (List.class.isAssignableFrom(originalGivenValue.getClass()))
        {
            List<?> list = (List<?>) originalGivenValue;
            if (!list.isEmpty())
            {
                keyClassName = list.get(0).getClass().getName();
            }
            dataType = DataInfo.TYPE_LIST;
        } else if (Map.class.isAssignableFrom(originalGivenValue.getClass()))
        {
            dataType = DataInfo.TYPE_MAP;
            Map<?, ?> map = (Map) originalGivenValue;
            if (!map.isEmpty())
            {
                for (Map.Entry<?, ?> entry : map.entrySet())
                {
                    keyClassName = entry.getKey().getClass().getName();
                    valueClassName = entry.getValue().getClass().getName();
                    break;
                }
            }
        } else if (Set.class.isAssignableFrom(originalGivenValue.getClass()))
        {
            Set<?> set = (Set<?>) originalGivenValue;
            if (!set.isEmpty())
            {
                Iterator<?> iterator = set.iterator();
                if (iterator.hasNext())
                {
                    keyClassName = iterator.next().getClass().getName();
                }
            }
            dataType = DataInfo.TYPE_SET;
        } else
        {
            dataType = DataInfo.TYPE_OBJECT;
            keyClassName = originalGivenValue.getClass().getName();
        }

        return keyClassName + INFO_DELIMITER +
                valueClassName + INFO_DELIMITER +
                dataType + NEW_VERSION + DELIMITER +
                cipherText;
    }

    @Override
    public DataInfo deserialize(String serializedText)
    {
        String[] infos = serializedText.split(INFO_DELIMITER);

        char type = infos[2].charAt(0);

        // if it is collection, no need to create the class object
        Class<?> keyClazz = null;
        String firstElement = infos[0];
        if (firstElement != null && firstElement.length() != 0)
        {
            try
            {
                keyClazz = Class.forName(firstElement);
            } catch (ClassNotFoundException e)
            {
                logInterceptor.onLog("MemoSerializer -> " + e.getMessage());
            }
        }

        Class<?> valueClazz = null;
        String secondElement = infos[1];
        if (secondElement != null && secondElement.length() != 0)
        {
            try
            {
                valueClazz = Class.forName(secondElement);
            } catch (ClassNotFoundException e)
            {
                logInterceptor.onLog("MemoSerializer -> " + e.getMessage());
            }
        }

        String cipherText = getCipherText(infos[infos.length - 1]);
        return new DataInfo(type, cipherText, keyClazz, valueClazz);
    }

    private String getCipherText(String serializedText)
    {
        int index = serializedText.indexOf(DELIMITER);
        if (index == -1)
        {
            throw new IllegalArgumentException("Text should contain delimiter");
        }
        return serializedText.substring(index + 1);
    }

}
