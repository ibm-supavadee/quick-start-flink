package org.myorg.quickstart;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.apache.flink.api.common.serialization.DeserializationSchema;
import org.apache.flink.api.common.typeinfo.TypeInformation;

import java.io.IOException;

public class CustomSchema implements DeserializationSchema<KafkaMessage> {
        private static final Logger LOG = LoggerFactory.getLogger(CustomSchema.class);

        static ObjectMapper objectMapper = new ObjectMapper();

        @Override
        public KafkaMessage deserialize(byte[] bytes) throws IOException {
                LOG.debug("msg:" +bytes.toString());
                return objectMapper.readValue(bytes, KafkaMessage.class);
        }

        @Override
        public boolean isEndOfStream(KafkaMessage kafkaMessage) {
                return false;
        }

        @Override
        public TypeInformation<KafkaMessage> getProducedType() {
                return TypeInformation.of(KafkaMessage.class);
        }
}