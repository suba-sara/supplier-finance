package com.hcl.capstoneserver.generator.id;

import org.hibernate.HibernateException;
import org.hibernate.engine.spi.SharedSessionContractImplementor;
import org.hibernate.id.enhanced.SequenceStyleGenerator;
import org.hibernate.internal.util.config.ConfigurationHelper;
import org.hibernate.service.ServiceRegistry;
import org.hibernate.type.LongType;
import org.hibernate.type.Type;
import org.springframework.data.mapping.MappingException;

import java.io.Serializable;
import java.util.Properties;

public class CustomIdGenerator extends SequenceStyleGenerator {

    public static final String PREFIX_PARAM = "none"; // customer defined prefix parameter (eg: A_, Stu_)
    public static final String PREFIX_DEFAULT_PARAM = "";
    private String prefix;
    public static final String NUMBER_FORMAT_PARAM = "numberFormat"; // customer defined zeros (eg: A-0001)
    public static final String NUMBER_FORMAT_DEFAULT_PARAM = "%d";
    private String numberFormat;

    @Override
    public void configure(Type type,
                          Properties properties,
                          ServiceRegistry serviceRegistry) throws MappingException {
        super.configure(
                LongType.INSTANCE,
                properties,
                serviceRegistry);
        prefix = ConfigurationHelper.getString(
                PREFIX_PARAM, properties, PREFIX_DEFAULT_PARAM
        );
        numberFormat = ConfigurationHelper.getString(
                NUMBER_FORMAT_PARAM, properties, NUMBER_FORMAT_DEFAULT_PARAM
        );
    }

    @Override
    public Serializable generate(SharedSessionContractImplementor session,
                                 Object object) throws HibernateException {
        return prefix + String.format(numberFormat, super.generate(session, object));
    }
}