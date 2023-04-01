package com.lemoreiradev.listadetarefa.domain.config;

import com.lemoreiradev.listadetarefa.domain.config.properties.RedisProperties;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.autoconfigure.cache.RedisCacheManagerBuilderCustomizer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.cache.RedisCacheConfiguration;
import org.springframework.data.redis.connection.RedisStandaloneConfiguration;
import org.springframework.data.redis.connection.jedis.JedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.GenericJackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.StringRedisSerializer;

import java.time.Duration;

import static com.lemoreiradev.listadetarefa.domain.service.PessoaService.INDEX_PESSOAS;



/*
 Nessa classe, estamos configurando o Redis com o Jedis.
 Primeiro, criamos um bean de conexão com o Redis.
 Em seguida, criamos um bean do RedisTemplate e
 configuramos os serializadores para as chaves e valores.
 Neste exemplo, estamos usando o StringRedisSerializer para as chaves
 e o GenericJackson2JsonRedisSerializer para os valores.
* */


@RequiredArgsConstructor
@Configuration
public class RedisConfig {

    private final RedisProperties redisProperties;

    /*
    * Esse é um método anotado com @Bean que cria e configura uma instância de JedisConnectionFactory.
    * Vamos entender linha a linha o que o código está fazendo:
      @Bean: é uma anotação que indica que o método é um bean gerenciado pelo Spring e deve ser registrado como tal no contexto de aplicação.
      JedisConnectionFactory: é a classe do Spring Data Redis que implementa a interface RedisConnectionFactory,
      responsável por criar conexões com o Redis.
      RedisStandaloneConfiguration: é uma classe do Spring Data Redis que define a configuração de uma instância do Redis.
      redisProperties.getHost(): é uma chamada a um objeto redisProperties que obtém o host do Redis a partir
      das propriedades de configuração da aplicação.
      redisProperties.getPort(): é uma chamada a um objeto redisProperties que obtém a porta do Redis a partir
      das propriedades de configuração da aplicação.
      return new JedisConnectionFactory(redisStandaloneConfiguration);: cria e retorna uma nova instância de JedisConnectionFactory,
      passando a configuração da instância do Redis criada anteriormente.

      Em resumo, esse método cria uma conexão com uma instância do Redis usando
      as configurações fornecidas nas propriedades de configuração da aplicação.
      Essa conexão será usada posteriormente pelo Spring Data Redis para realizar operações de leitura e gravação no Redis.*/

    @Bean
    JedisConnectionFactory jedisConnectionFactory() {
        RedisStandaloneConfiguration redisStandaloneConfiguration = new RedisStandaloneConfiguration();
        redisStandaloneConfiguration.setHostName(redisProperties.getHost());
        redisStandaloneConfiguration.setPort(redisProperties.getPort());
        return new JedisConnectionFactory(redisStandaloneConfiguration);
    }


    /*
    Aqui está uma explicação do que cada linha está fazendo:

    RedisTemplate<String, Object> redisTemplate = new RedisTemplate<>();:
    Cria um novo objeto RedisTemplate com chaves do tipo String e valores do tipo Object.
    redisTemplate.setConnectionFactory(jedisConnectionFactory());:
    Configura a conexão de fábrica do RedisTemplate com a instância de conexão Redis obtida a partir do método jedisConnectionFactory().
    redisTemplate.setKeySerializer(new StringRedisSerializer());:
    Configura o serializador para as chaves, aqui utilizando o StringRedisSerializer padrão.
    redisTemplate.setValueSerializer(new GenericJackson2JsonRedisSerializer());:
    Configura o serializador para os valores, aqui utilizando o GenericJackson2JsonRedisSerializer
    que permite serializar/deserializar objetos Java em JSON.
    Em resumo, esse método cria e configura um RedisTemplate para ser utilizado na aplicação
    para armazenar e recuperar objetos serializados em JSON no Redis.
    * */


    @Bean
    RedisTemplate<String, Object> redisTemplate() {
        RedisTemplate<String, Object> redisTemplate = new RedisTemplate<>();
        redisTemplate.setConnectionFactory(jedisConnectionFactory());
        redisTemplate.setKeySerializer(new StringRedisSerializer());
        redisTemplate.setValueSerializer(new GenericJackson2JsonRedisSerializer());
        return redisTemplate;
    }



    /*
   @Bean: é uma anotação do Spring que indica que este método retorna um
   objeto que deve ser gerenciado pelo contêiner do Spring.
   public RedisCacheManagerBuilderCustomizer redisCacheManagerBuilderCustomizer():
   um método público que retorna um objeto do tipo RedisCacheManagerBuilderCustomizer.
   Esse método é invocado pelo contêiner do Spring para criar e gerenciar o objeto.
   return (builder) -> builder: o método retorna uma função lambda que recebe um objeto do tipo RedisCacheManagerBuilder.
   Esse objeto permite a personalização da configuração do gerenciador de cache do Redis.
   .withCacheConfiguration(INDEX_PESSOAS, RedisCacheConfiguration.defaultCacheConfig().entryTtl(Duration.ofMinutes(1))):
   adiciona uma configuração de cache com o nome INDEX_PESSOAS e uma expiração de tempo de 1 minuto.
   .withCacheConfiguration("OUTRO_CACHE_QUALQUER", RedisCacheConfiguration.defaultCacheConfig().entryTtl(Duration.ofMinutes(5))):
   adiciona uma configuração de cache com o nome "OUTRO_CACHE_QUALQUER" e uma expiração de tempo de 5 minutos.
   Portanto, esse método personaliza o gerenciador de cache do Redis adicionando duas configurações de cache diferentes.
    * */

    @Bean
    public RedisCacheManagerBuilderCustomizer redisCacheManagerBuilderCustomizer() {
        return (builder) -> builder
                .withCacheConfiguration(INDEX_PESSOAS,
                        RedisCacheConfiguration.defaultCacheConfig().entryTtl(Duration.ofMinutes(1)))
                .withCacheConfiguration("OUTRO_CACHE_QUALQUER",
                        RedisCacheConfiguration.defaultCacheConfig().entryTtl(Duration.ofMinutes(5)));
    }
}